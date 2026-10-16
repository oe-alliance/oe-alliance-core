/* Internal-use Amlogic-derived converter; see LICENSE.amlogic and docs/CODECS.txt. */
#ifndef DREAM_H263_H
#define DREAM_H263_H

#include <gst/gst.h>

typedef struct DreamH263 DreamH263;

DreamH263 *dream_h263_new(void);
void dream_h263_free(DreamH263 *state);
void dream_h263_reset(DreamH263 *state);
void dream_h263_get_size(const DreamH263 *state, guint *width, guint *height);
GstBuffer *dream_h263_transform(DreamH263 *state, GstBuffer *input,
	gboolean sorenson, GError **error);

#endif

