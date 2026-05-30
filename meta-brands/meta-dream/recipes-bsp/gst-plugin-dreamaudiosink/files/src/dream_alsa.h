#ifndef __DREAM_ALSA_H__
#define __DREAM_ALSA_H__

#include <stdint.h>
#include <stddef.h>

#ifdef __cplusplus
extern "C" {
#endif

typedef struct DreamAlsa DreamAlsa;

/*
 * device may be NULL -> defaults to "default".
 * Opens lazily; first set_params() actually configures hardware.
 */
DreamAlsa *dream_alsa_new(const char *device);
void       dream_alsa_free(DreamAlsa *a);

/*
 * Configure / reconfigure hardware. Safe to call repeatedly; tears down
 * and reopens if rate / channels changed.
 * passthrough != 0 disables software volume / S16 conversion expectations
 * (caller passes already-framed IEC61937 bytes).
 */
int dream_alsa_set_params(DreamAlsa *a,
                          unsigned int sample_rate,
                          unsigned int channels,
                          unsigned int bytes_per_sample,
                          int passthrough);

/*
 * Blocking write. Returns bytes written or negative on hard error.
 * Recovers from XRUN automatically.
 */
int dream_alsa_write(DreamAlsa *a, const uint8_t *data, size_t size);

void dream_alsa_drop(DreamAlsa *a);    /* discard buffered audio */

/* Software volume scaler applied to S16 PCM in dream_alsa_write.
 * level: 0..100 (linear). AMLogic Master mixer is pinned at max so
 * this is the only effective volume control. No-op for passthrough. */
void dream_alsa_set_volume(DreamAlsa *a, int level);

#ifdef __cplusplus
}
#endif

#endif
