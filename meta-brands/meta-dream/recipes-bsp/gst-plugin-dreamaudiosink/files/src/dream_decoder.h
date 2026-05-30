#ifndef __DREAM_DECODER_H__
#define __DREAM_DECODER_H__

#include <stdint.h>
#include <stddef.h>

#ifdef __cplusplus
extern "C" {
#endif

typedef enum {
    DREAM_DECODER_OUTPUT_PCM,
    DREAM_DECODER_OUTPUT_IEC61937,
} DreamDecoderOutputType;

typedef void (*DreamDecoderOutputCallback)(
    DreamDecoderOutputType type,
    unsigned int            sample_rate,
    unsigned int            channels,
    const uint8_t          *data,
    size_t                  size,
    int64_t                 pts,
    void                   *user_data);

typedef struct DreamDecoder DreamDecoder;

/*
 * codec_id: enum AVCodecID (cast to int to keep this header FFmpeg-free).
 * sample_rate / channels: hint from upstream (caps); may be overridden by
 * what the decoder reports per frame.
 * cb: invoked once per decoded output chunk.
 */
DreamDecoder *dream_decoder_new(int codec_id,
                                int sample_rate,
                                int channels,
                                DreamDecoderOutputCallback cb,
                                void *user_data);

void dream_decoder_free(DreamDecoder *dec);

/*
 * Decode one input frame (typically one PES payload).
 * Returns 0 on success, negative on hard error.
 * Soft errors (decoder needs more data) are reported as 0.
 */
int dream_decoder_decode(DreamDecoder *dec,
                         const uint8_t *data,
                         int size,
                         int64_t pts,
                         int64_t dts);

#ifdef __cplusplus
}
#endif

#endif
