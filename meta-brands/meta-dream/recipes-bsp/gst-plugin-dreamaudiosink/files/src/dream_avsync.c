#ifdef HAVE_CONFIG_H
#include "config.h"
#endif

#include "dream_avsync.h"
#include "dream_log.h"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define AVSYNC_DBG(...) dream_log("avsync", __VA_ARGS__)

#define TSYNC_ENABLE            "/sys/class/tsync/enable"
#define TSYNC_MODE              "/sys/class/tsync/mode"
#define TSYNC_PTS_AUDIO         "/sys/class/tsync/pts_audio"
#define TSYNC_DISCONTINUE       "/sys/class/tsync/discontinue"

struct DreamAvsync {
    DreamAvsyncMode mode;
};

static void write_str(const char *path, const char *s)
{
    FILE *f = fopen(path, "w");
    if (!f) return;
    fputs(s, f);
    fclose(f);
}

static void write_int(const char *path, int v)
{
    char buf[32];
    snprintf(buf, sizeof(buf), "%d", v);
    write_str(path, buf);
}

DreamAvsync *dream_avsync_new(DreamAvsyncMode mode)
{
    DreamAvsync *a = calloc(1, sizeof(*a));
    if (!a) return NULL;
    a->mode = mode;

    /* Kernel tsync sysfs pts_audio writes don't actually advance the kernel
     * audio clock - that path only works when audio ES is fed through
     * /dev/amstream_abuf + audiodsp0 HW decoder, which we explicitly do NOT
     * do (no kernel route to audio output without extra amaudio mixer setup,
     * see CoreELEC AESinkALSA which also uses pure userspace ALSA).
     *
     * VMASTER (0) = userspace AV sync loop in dream_alsa drives correction
     *               by comparing apts-at-speaker vs /sys/class/tsync/pts_video.
     * AMASTER (1) = sysfs pts_audio path - left in for diagnostics, no effect.
     * PCRMASTER (2) = same situation.
     *
     * Default to VMASTER: disable kernel tsync so it doesn't fight our
     * userspace loop (kernel would otherwise pause video on absent
     * pts_audio updates). */
    if (mode == DREAM_AVSYNC_MODE_VMASTER) {
        write_int(TSYNC_ENABLE, 0);
        AVSYNC_DBG("init: kernel tsync DISABLED (userspace sync via dream_alsa)");
    } else {
        write_int(TSYNC_ENABLE, 1);
        write_int(TSYNC_MODE,   (int)mode);
        AVSYNC_DBG("init: kernel tsync enabled, mode=%d", (int)mode);
    }
    return a;
}

void dream_avsync_free(DreamAvsync *a)
{
    if (!a) return;
    /* Re-enable kernel tsync if we disabled it on init. Other paths
     * (Live-TV via enigma2 eAlsaOutput, kernel video decoder for the
     * next service) expect tsync at its default enabled state — leaving
     * it disabled means the next pipeline reads stale pts_video and
     * sees pathological drift on the first audio frames. */
    if (a->mode == DREAM_AVSYNC_MODE_VMASTER) {
        write_int(TSYNC_ENABLE, 1);
        AVSYNC_DBG("free: kernel tsync re-enabled");
    }
    free(a);
}

void dream_avsync_checkin_audio_pts(DreamAvsync *a, uint32_t pts_90khz)
{
    /* No-op: writing pts_audio fights kernel pcrmaster. */
    (void)a;
    (void)pts_90khz;
}

void dream_avsync_signal_discontinuity(DreamAvsync *a)
{
    (void)a;
    write_int(TSYNC_DISCONTINUE, 1);
    AVSYNC_DBG("discontinuity signalled");
}
