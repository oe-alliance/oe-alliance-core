#ifdef HAVE_CONFIG_H
#include "config.h"
#endif

#include "dream_alsa.h"
#include "dream_log.h"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <fcntl.h>
#include <errno.h>

#include <alsa/asoundlib.h>

#define ALSA_DBG(...) dream_log("alsa", __VA_ARGS__)

#define ALSA_OPEN_MAX_RETRIES   5
#define ALSA_OPEN_RETRY_MS      50
/* 1024 × 8 = 170ms @ 48kHz, matches /etc/asound.conf dmix slave. */
#define ALSA_PERIOD_FRAMES_AT_48K  1024
#define ALSA_NUM_PERIODS           8

#define DREAM_ALSA_SCRATCH_BYTES (256 * 1024)  /* fits any reasonable audio chunk */

struct DreamAlsa {
    char         *device;
    snd_pcm_t    *handle;
    unsigned int  rate;
    unsigned int  channels;
    unsigned int  bytes_per_sample;
    int           passthrough;
    int           configured;
    int           nonblocking;
    snd_pcm_uframes_t buffer_size;
    int           volume_q15;     /* 0..32768 - linear gain for S16 PCM */
    uint8_t      *scratch;        /* reusable scaled-PCM buffer */
    size_t        scratch_size;

    /* anchor + sustained-lag recovery state. */
    int           anchor_armed;
    int           pts_video_fd;
    size_t        skip_bytes_remaining;       /* drain budget from anchor catch-up */
    int64_t       last_reanchor_ms;           /* 5s cooldown after re-arm */
    int64_t       last_huge_gap_ms;           /* 1s throttle in anchor branch */
    int64_t       drift_outside_since_ms;     /* when |av| left ±1000ms (0 = inside) */
    int64_t       last_sync_log_ms;           /* 30s heartbeat cadence */

    /* Lead = (apts−first_apts) − (vpts−first_vpts) — cross-epoch safe. */
    int           delta_baseline_armed;
    int64_t       first_apts;
    int64_t       first_vpts;
};

static int64_t monotonic_ms(void);

DreamAlsa *dream_alsa_new(const char *device)
{
    DreamAlsa *a = calloc(1, sizeof(*a));
    if (!a) return NULL;
    a->device = strdup(device && *device ? device : "default");
    if (!a->device) { free(a); return NULL; }
    a->volume_q15 = 32768;  /* full volume by default */
    a->pts_video_fd = -1;
    a->scratch = malloc(DREAM_ALSA_SCRATCH_BYTES);
    a->scratch_size = a->scratch ? DREAM_ALSA_SCRATCH_BYTES : 0;
    return a;
}

void dream_alsa_reset_anchor(DreamAlsa *a)
{
    if (!a) return;
    a->anchor_armed = 1;
    a->skip_bytes_remaining = 0;
    a->last_sync_log_ms = monotonic_ms();
    a->delta_baseline_armed = 1;
    a->first_apts = 0;
    a->first_vpts = 0;
}

/* Cached-fd reader for /sys/class/tsync/pts_video. -1 on failure. */
static int64_t read_pts_video(DreamAlsa *a)
{
    if (a->pts_video_fd < 0) {
        a->pts_video_fd = open("/sys/class/tsync/pts_video", O_RDONLY | O_CLOEXEC);
        if (a->pts_video_fd < 0) return -1;
    }
    char buf[32];
    if (lseek(a->pts_video_fd, 0, SEEK_SET) < 0) return -1;
    ssize_t n = read(a->pts_video_fd, buf, sizeof(buf) - 1);
    if (n <= 0) return -1;
    buf[n] = '\0';
    unsigned int x = 0;
    if (sscanf(buf, "0x%x", &x) != 1) return -1;
    return (int64_t)x;
}

int64_t dream_alsa_get_delay_pts(DreamAlsa *a)
{
    if (!a || !a->handle || !a->rate) return 0;
    snd_pcm_sframes_t df = 0;
    if (snd_pcm_delay(a->handle, &df) < 0 || df < 0) return 0;
    return (int64_t)df * 90000LL / (int64_t)a->rate;
}

static int64_t monotonic_ms(void)
{
    struct timespec ts;
    clock_gettime(CLOCK_MONOTONIC, &ts);
    return (int64_t)ts.tv_sec * 1000 + ts.tv_nsec / 1000000;
}

/* Anchor uses this to delay audio when it leads pts_video. */
static void push_silence_ms(DreamAlsa *a, int ms)
{
    static const uint8_t sil[8192] = {0};
    const size_t frame_bytes = (size_t)a->channels *
        (a->bytes_per_sample ? a->bytes_per_sample : 2);
    if (frame_bytes == 0) return;
    snd_pcm_uframes_t frames_total =
        (snd_pcm_uframes_t)((int64_t)ms * a->rate / 1000);
    snd_pcm_uframes_t chunk_frames = sizeof(sil) / frame_bytes;
    while (frames_total > 0) {
        snd_pcm_uframes_t n = frames_total > chunk_frames ? chunk_frames : frames_total;
        snd_pcm_sframes_t w = snd_pcm_writei(a->handle, sil, n);
        if (w < 0) {
            if (snd_pcm_recover(a->handle, (int)w, 1) < 0) break;
            continue;
        }
        if (w == 0) {
            if (a->nonblocking) break;
            usleep(1000);
            continue;
        }
        frames_total -= (snd_pcm_uframes_t)w;
    }
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
        usleep(10000);
    }
    a->configured = 0;
    a->buffer_size = 0;
}

void dream_alsa_free(DreamAlsa *a)
{
    if (!a) return;
    dream_alsa_close_handle(a);
    if (a->pts_video_fd >= 0) { close(a->pts_video_fd); a->pts_video_fd = -1; }
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

    if (!a->nonblocking) {
        err = snd_pcm_nonblock(a->handle, 0);
        if (err < 0) ALSA_DBG("set block: %s", snd_strerror(err));
    }
    return 0;
}

void dream_alsa_set_nonblocking(DreamAlsa *a, int enabled)
{
    if (!a) return;
    a->nonblocking = enabled ? 1 : 0;
    if (a->handle) {
        int err = snd_pcm_nonblock(a->handle, a->nonblocking);
        if (err < 0)
            ALSA_DBG("set %sblocking: %s", a->nonblocking ? "non-" : "",
                     snd_strerror(err));
    }
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

    snd_pcm_hw_params_t *hw;
    snd_pcm_hw_params_alloca(&hw);
    unsigned int rate = sample_rate, chans = channels;
    snd_pcm_hw_params_any(a->handle, hw);
    snd_pcm_hw_params_set_access(a->handle, hw, SND_PCM_ACCESS_RW_INTERLEAVED);
    snd_pcm_hw_params_set_format(a->handle, hw, SND_PCM_FORMAT_S16);
    snd_pcm_hw_params_set_rate_near(a->handle, hw, &rate, NULL);
    snd_pcm_hw_params_set_channels_near(a->handle, hw, &chans);

    snd_pcm_uframes_t period_size =
        ((snd_pcm_uframes_t)rate * ALSA_PERIOD_FRAMES_AT_48K) / 48000;
    snd_pcm_uframes_t buffer_size = period_size * ALSA_NUM_PERIODS;
    snd_pcm_hw_params_set_period_size_near(a->handle, hw, &period_size, NULL);
    snd_pcm_hw_params_set_buffer_size_near(a->handle, hw, &buffer_size);

    int err = snd_pcm_hw_params(a->handle, hw);
    if (err < 0) {
        ALSA_DBG("hw_params(rate=%u ch=%u): %s", sample_rate, channels, snd_strerror(err));
        return -1;
    }
    snd_pcm_hw_params_get_period_size(hw, &period_size, NULL);
    snd_pcm_hw_params_get_buffer_size(hw, &buffer_size);
    ALSA_DBG("hw_params: %lu frames buf, %lu frames period",
             (unsigned long)buffer_size, (unsigned long)period_size);

    snd_pcm_prepare(a->handle);
    /* Default sw_params; silence-pad fights dmix slave drain rate. */

    a->rate             = sample_rate;
    a->channels         = channels;
    a->bytes_per_sample = bytes_per_sample;
    a->passthrough      = passthrough;
    a->configured       = 1;
    a->buffer_size      = buffer_size;
    a->anchor_armed     = 1;
    a->skip_bytes_remaining = 0;
    a->last_sync_log_ms = monotonic_ms();
    a->delta_baseline_armed = 1;
    a->first_apts       = 0;
    a->first_vpts       = 0;

    ALSA_DBG("configured: rate=%u ch=%u bps=%u passthrough=%d",
             a->rate, a->channels, a->bytes_per_sample, a->passthrough);
    return 0;
}

int dream_alsa_write(DreamAlsa *a, const uint8_t *data, size_t size, int64_t pts_90k)
{
    if (!a || !a->handle || !a->configured || !data || size == 0)
        return -1;

    const size_t frame_bytes = (size_t)a->channels * (a->bytes_per_sample ? a->bytes_per_sample : 2);
    if (frame_bytes == 0 || (size % frame_bytes) != 0)
        return -1;

    /* The AMLogic dmix slave can keep reporting RUNNING after an underrun
     * while its hardware pointer races far beyond the application pointer.
     * A blocking snd_pcm_writei() then waits forever on /dev/snd/timer.  The
     * opt-in non-blocking user resets this impossible ring state and keeps
     * its video producer alive. */
    if (a->nonblocking && a->buffer_size > 0) {
        snd_pcm_sframes_t delay = 0;
        int err = snd_pcm_delay(a->handle, &delay);
        if (err < 0) {
            err = snd_pcm_recover(a->handle, err, 1);
            if (err < 0) return 0;
        } else if (delay < -(snd_pcm_sframes_t)a->buffer_size) {
            ALSA_DBG("invalid ring delay %ld frames, resetting PCM",
                     (long)delay);
            snd_pcm_drop(a->handle);
            if (snd_pcm_prepare(a->handle) < 0) return 0;
            dream_alsa_reset_anchor(a);
        }
    }

    /* Drain BEHIND skip budget before any new anchor / drift check fires. */
    if (a->skip_bytes_remaining > 0) {
        size_t drop = a->skip_bytes_remaining > size ? size : a->skip_bytes_remaining;
        drop -= (drop % frame_bytes);
        if (drop > 0) {
            a->skip_bytes_remaining -= drop;
            data += drop;
            size -= drop;
            if (size == 0) return (int)drop;
        }
    }

    /* apts_speaker = pts at the speaker (queue tail − snd_pcm_delay). */
    int64_t alsa_delay_pts = 0;
    if (a->rate) {
        snd_pcm_sframes_t df = 0;
        if (snd_pcm_delay(a->handle, &df) == 0 && df > 0)
            alsa_delay_pts = (int64_t)df * 90000LL / (int64_t)a->rate;
    }
    int64_t apts_speaker = (pts_90k >= 0) ? (pts_90k - alsa_delay_pts) : -1;

    if (a->anchor_armed && apts_speaker >= 0 && a->rate && !a->passthrough) {
        int64_t pts_v = read_pts_video(a);
        if (pts_v >= 0) {
            if (a->delta_baseline_armed) {
                a->first_apts = apts_speaker;
                a->first_vpts = pts_v;
                a->delta_baseline_armed = 0;
                ALSA_DBG("anchor: delta baseline first_apts=%lx first_vpts=%lx",
                         (long)a->first_apts, (long)a->first_vpts);
            }
            int64_t apts_delta = apts_speaker - a->first_apts;
            int64_t vpts_delta = pts_v        - a->first_vpts;
            int32_t lead_ms = (int32_t)((uint32_t)apts_delta - (uint32_t)vpts_delta) / 90;
            ALSA_DBG("anchor: lead=%+dms vpts=%lx apts=%lx (Δv=%+lldms Δa=%+lldms)",
                     lead_ms, (long)pts_v, (long)apts_speaker,
                     (long long)(vpts_delta / 90), (long long)(apts_delta / 90));
            /* HUGE-gap throttled 1s — PlutoTV stitched-HLS keeps feeding
             * stale-PTS audio post-stitch; unthrottled flush loop = mute. */
            int64_t now_ms_hg = monotonic_ms();
            if ((lead_ms > 2000 || lead_ms < -2000)
                && now_ms_hg - a->last_huge_gap_ms > 1000)
            {
                a->last_huge_gap_ms = now_ms_hg;
                snd_pcm_drop(a->handle);
                snd_pcm_prepare(a->handle);
                ALSA_DBG("anchor: HUGE gap %+dms → ALSA flush, drop buffer (1s throttle)", lead_ms);
                return (int)size;
            }
            if (lead_ms > 50) {
                int adj = lead_ms > 2000 ? 2000 : lead_ms;
                push_silence_ms(a, adj);
                ALSA_DBG("anchor: pushed %dms silence (was %+dms ahead)", adj, lead_ms);
            } else if (lead_ms < -50) {
                int adj = lead_ms < -3000 ? -3000 : lead_ms;
                a->skip_bytes_remaining =
                    (size_t)((int64_t)(-adj) * a->rate / 1000) * frame_bytes;
                ALSA_DBG("anchor: queued %dms skip (was %+dms behind)", -adj, lead_ms);
                size_t drop = a->skip_bytes_remaining > size ? size : a->skip_bytes_remaining;
                drop -= (drop % frame_bytes);
                a->skip_bytes_remaining -= drop;
                data += drop;
                size -= drop;
                if (size == 0) { a->anchor_armed = 0; return (int)drop; }
            }
            a->anchor_armed = 0;
        }
    }

    /* Post-anchor: sustained-lag recovery only. */
    if (!a->anchor_armed && apts_speaker >= 0 && a->rate && !a->passthrough
        && !a->delta_baseline_armed)
    {
        int64_t pts_v = read_pts_video(a);
        if (pts_v >= 0) {
            int64_t apts_delta = apts_speaker - a->first_apts;
            int64_t vpts_delta = pts_v        - a->first_vpts;
            int32_t av_ms = (int32_t)((uint32_t)apts_delta - (uint32_t)vpts_delta) / 90;
            int64_t now_ms = monotonic_ms();

            /* 30s heartbeat. */
            if (now_ms - a->last_sync_log_ms > 30000) {
                ALSA_DBG("vpts=%lx apts=%lx av=%+dms hw_delay=%lldms [heartbeat]",
                         (long)pts_v, (long)apts_speaker, av_ms,
                         (long long)(alsa_delay_pts / 90));
                a->last_sync_log_ms = now_ms;
            }

            /* Re-arm anchor when |av| > 1000ms held >=2s; 5s cooldown. */
            int32_t abs_av = av_ms < 0 ? -av_ms : av_ms;
            if (abs_av > 1000) {
                if (a->drift_outside_since_ms == 0)
                    a->drift_outside_since_ms = now_ms;
            } else {
                a->drift_outside_since_ms = 0;
            }
            if (a->drift_outside_since_ms != 0
                && now_ms - a->drift_outside_since_ms >= 2000
                && now_ms - a->last_reanchor_ms > 5000)
            {
                a->last_reanchor_ms       = now_ms;
                a->drift_outside_since_ms = 0;
                a->anchor_armed           = 1;
                ALSA_DBG("sustained lag av=%+dms → re-arm anchor", av_ms);
            }
        }
    }

    /* SW volume for S16 PCM. Bypassed for passthrough (would corrupt
     * IEC61937 bursts). Race on volume_q15 is tolerated. */
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
        if (n == -EAGAIN && a->nonblocking)
            return (int)written;
        if (n < 0) {
            int err = snd_pcm_recover(a->handle, (int)n, 0);
            if (err < 0) {
                ALSA_DBG("recover: %s", snd_strerror(err));
                return -1;
            }
            continue;
        }
        if (n == 0 && a->nonblocking)
            return (int)written;
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
