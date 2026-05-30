#ifdef HAVE_CONFIG_H
#include "config.h"
#endif

#include "dream_alsa.h"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <errno.h>

#include <alsa/asoundlib.h>

/* Tee debug lines to /tmp/dream.log so we can inspect over ssh without
 * capturing /dev/console (enigma2 main process logs go there). */
static FILE *_dream_log_fp(void)
{
    static FILE *fp = NULL;
    if (!fp) {
        fp = fopen("/tmp/dream.log", "a");
        if (fp) setvbuf(fp, NULL, _IOLBF, 0);
    }
    return fp;
}
#define ALSA_DBG(...) do { \
    fprintf(stderr, "[dream_alsa] " __VA_ARGS__); fputc('\n', stderr); \
    FILE *_fp = _dream_log_fp(); \
    if (_fp) { fprintf(_fp, "[dream_alsa] " __VA_ARGS__); fputc('\n', _fp); } \
} while (0)

#define ALSA_OPEN_MAX_RETRIES   5
#define ALSA_OPEN_RETRY_MS      50
/* 500ms HW buffer matches lib/dvb/alsa.cpp Live-TV path. Below that and
 * any decoder hiccup causes underrun. */
#define ALSA_BUFFER_TIME_US     500000   /* 500 ms */

#define DREAM_ALSA_SCRATCH_BYTES (256 * 1024)  /* fits any reasonable audio chunk */

struct DreamAlsa {
    char         *device;
    snd_pcm_t    *handle;
    unsigned int  rate;
    unsigned int  channels;
    unsigned int  bytes_per_sample;
    int           passthrough;
    int           configured;
    int           volume_q15;     /* 0..32768 - linear gain for S16 PCM */
    uint8_t      *scratch;        /* reusable scaled-PCM buffer */
    size_t        scratch_size;
};

DreamAlsa *dream_alsa_new(const char *device)
{
    DreamAlsa *a = calloc(1, sizeof(*a));
    if (!a) return NULL;
    a->device = strdup(device && *device ? device : "default");
    if (!a->device) { free(a); return NULL; }
    a->volume_q15 = 32768;  /* full volume by default */
    /* Allocate scratch ONCE - no realloc per audio frame to avoid heap
     * churn / corruption seen with per-call malloc patterns. */
    a->scratch = malloc(DREAM_ALSA_SCRATCH_BYTES);
    a->scratch_size = a->scratch ? DREAM_ALSA_SCRATCH_BYTES : 0;
    return a;
}

void dream_alsa_set_volume(DreamAlsa *a, int level)
{
    if (!a) return;
    if (level < 0)   level = 0;
    if (level > 100) level = 100;
    a->volume_q15 = (level * 32768) / 100;
}

static void dream_alsa_close_handle(DreamAlsa *a)
{
    if (a->handle) {
        snd_pcm_drop(a->handle);
        snd_pcm_close(a->handle);
        a->handle = NULL;
        usleep(10000);  /* let device release fully before re-open */
    }
    a->configured = 0;
}

void dream_alsa_free(DreamAlsa *a)
{
    if (!a) return;
    dream_alsa_close_handle(a);
    free(a->scratch);
    free(a->device);
    free(a);
}

static int dream_alsa_open_handle(DreamAlsa *a)
{
    int err = 0;
    for (int i = 0; i < ALSA_OPEN_MAX_RETRIES; ++i) {
        err = snd_pcm_open(&a->handle, a->device,
                           SND_PCM_STREAM_PLAYBACK, SND_PCM_NONBLOCK);
        if (err == 0) {
            if (i > 0) ALSA_DBG("open '%s' ok after %d retries", a->device, i);
            break;
        }
        if (err == -EBUSY && i < ALSA_OPEN_MAX_RETRIES - 1) {
            ALSA_DBG("open '%s' busy, retry %d/%d", a->device, i + 1, ALSA_OPEN_MAX_RETRIES);
            usleep(ALSA_OPEN_RETRY_MS * 1000);
            a->handle = NULL;
            continue;
        }
        ALSA_DBG("open '%s' failed: %s", a->device, snd_strerror(err));
        a->handle = NULL;
        return err;
    }
    if (!a->handle) return -1;

    err = snd_pcm_nonblock(a->handle, 0);
    if (err < 0) ALSA_DBG("set block: %s", snd_strerror(err));
    return 0;
}

int dream_alsa_set_params(DreamAlsa *a,
                          unsigned int sample_rate,
                          unsigned int channels,
                          unsigned int bytes_per_sample,
                          int passthrough)
{
    if (!a) return -1;

    if (a->configured
        && a->rate == sample_rate
        && a->channels == channels
        && a->bytes_per_sample == bytes_per_sample
        && a->passthrough == passthrough)
        return 0;

    dream_alsa_close_handle(a);
    if (dream_alsa_open_handle(a) < 0) return -1;

    int err = snd_pcm_set_params(a->handle,
                                 SND_PCM_FORMAT_S16,
                                 SND_PCM_ACCESS_RW_INTERLEAVED,
                                 channels,
                                 sample_rate,
                                 1,
                                 ALSA_BUFFER_TIME_US);
    if (err < 0) {
        ALSA_DBG("set_params(rate=%u ch=%u): %s", sample_rate, channels, snd_strerror(err));
        return -1;
    }

    a->rate             = sample_rate;
    a->channels         = channels;
    a->bytes_per_sample = bytes_per_sample;
    a->passthrough      = passthrough;
    a->configured       = 1;

    ALSA_DBG("configured: rate=%u ch=%u bps=%u passthrough=%d",
             a->rate, a->channels, a->bytes_per_sample, a->passthrough);
    return 0;
}

int dream_alsa_write(DreamAlsa *a, const uint8_t *data, size_t size)
{
    if (!a || !a->handle || !a->configured || !data || size == 0)
        return -1;

    const size_t frame_bytes = (size_t)a->channels * (a->bytes_per_sample ? a->bytes_per_sample : 2);
    if (frame_bytes == 0 || (size % frame_bytes) != 0)
        return -1;

    /* Software volume for S16 PCM. Bypass for passthrough (IEC61937 bursts
     * would be corrupted by scaling). volume_q15 read is racy with
     * dream_alsa_set_volume from UI thread - tolerated, worst case one
     * frame at wrong gain. Scale into scratch to avoid mutating caller buf. */
    const uint8_t *play_data = data;
    const int vol_q15 = a->volume_q15;
    if (!a->passthrough && vol_q15 < 32768 && a->bytes_per_sample == 2 &&
        a->scratch && a->scratch_size >= size)
    {
        const int16_t *src = (const int16_t *)data;
        int16_t *dst = (int16_t *)a->scratch;
        size_t samples = size / 2;
        if (vol_q15 == 0) {
            memset(dst, 0, size);
        } else {
            for (size_t i = 0; i < samples; ++i) {
                int32_t v = ((int32_t)src[i] * vol_q15) >> 15;
                if (v >  32767) v =  32767;
                if (v < -32768) v = -32768;
                dst[i] = (int16_t)v;
            }
        }
        play_data = a->scratch;
    }

    snd_pcm_uframes_t frames_total = size / frame_bytes;
    size_t            offset       = 0;
    size_t            written      = 0;

    while (frames_total > 0) {
        snd_pcm_sframes_t n = snd_pcm_writei(a->handle, play_data + offset, frames_total);
        if (n < 0) {
            int err = snd_pcm_recover(a->handle, (int)n, 0);
            if (err < 0) {
                ALSA_DBG("recover: %s", snd_strerror(err));
                return -1;
            }
            continue;
        }
        offset       += (size_t)n * frame_bytes;
        written      += (size_t)n * frame_bytes;
        frames_total -= (snd_pcm_uframes_t)n;
    }

    return (int)written;
}

void dream_alsa_drop(DreamAlsa *a)
{
    if (a && a->handle) {
        snd_pcm_drop(a->handle);
        snd_pcm_prepare(a->handle);
    }
}
