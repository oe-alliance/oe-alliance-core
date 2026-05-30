#ifndef __DREAM_AVSYNC_H__
#define __DREAM_AVSYNC_H__

#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

typedef enum {
    DREAM_AVSYNC_MODE_VMASTER   = 0,
    DREAM_AVSYNC_MODE_AMASTER   = 1,
    DREAM_AVSYNC_MODE_PCRMASTER = 2,
} DreamAvsyncMode;

typedef struct DreamAvsync DreamAvsync;

DreamAvsync *dream_avsync_new(DreamAvsyncMode mode);
void         dream_avsync_free(DreamAvsync *a);

/* Tell kernel which audio PTS (90 kHz) we just rendered — diagnostic only,
 * doesn't drive the kernel audio clock in our userspace-ALSA path. */
void         dream_avsync_checkin_audio_pts(DreamAvsync *a, uint32_t pts_90khz);

/* Tell kernel that a PTS discontinuity is coming (zap, CSA switch, seek). */
void         dream_avsync_signal_discontinuity(DreamAvsync *a);

#ifdef __cplusplus
}
#endif

#endif
