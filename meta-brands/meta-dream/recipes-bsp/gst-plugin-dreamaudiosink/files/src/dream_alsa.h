#ifndef __DREAM_ALSA_H__
#define __DREAM_ALSA_H__

#include <stdint.h>
#include <stddef.h>

#ifdef __cplusplus
extern "C" {
#endif

typedef struct DreamAlsa DreamAlsa;

/* device NULL → "default". HW configured lazily on first set_params(). */
DreamAlsa *dream_alsa_new(const char *device);
void       dream_alsa_free(DreamAlsa *a);

/* Reconfigure if any param changed. passthrough != 0 = IEC61937 bytes. */
int dream_alsa_set_params(DreamAlsa *a,
                          unsigned int sample_rate,
                          unsigned int channels,
                          unsigned int bytes_per_sample,
                          int passthrough);

/* Optional non-blocking mode for callers which must keep feeding a separate
 * video decoder even when the AMLogic ALSA timer temporarily stalls. */
void dream_alsa_set_nonblocking(DreamAlsa *a, int enabled);

/* Blocking write with auto XRUN recovery. pts_90k = -1 if unknown.
 * Runs anchor + tier drift correction against /sys/class/tsync/pts_video. */
int dream_alsa_write(DreamAlsa *a, const uint8_t *data, size_t size, int64_t pts_90k);

void dream_alsa_drop(DreamAlsa *a);

/* Re-arm anchor on stream / seek / codec change. */
void dream_alsa_reset_anchor(DreamAlsa *a);

/* ALSA queue depth in 90 kHz ticks. 0 if not configured. */
int64_t dream_alsa_get_delay_pts(DreamAlsa *a);

/* SW volume 0..100 for S16 PCM. No-op for passthrough. */
void dream_alsa_set_volume(DreamAlsa *a, int level);

#ifdef __cplusplus
}
#endif

#endif
