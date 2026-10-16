/* SPDX-License-Identifier: LGPL-2.1-or-later */
#ifndef DREAM_OVERLAY_H
#define DREAM_OVERLAY_H

#include <gst/video/video.h>
#include <gst/video/video-overlay-composition.h>

G_BEGIN_DECLS

#define GST_TYPE_DREAM_OVERLAY_COMPOSITOR (gst_dream_overlay_compositor_get_type())
#define GST_DREAM_OVERLAY_COMPOSITOR(obj) (G_TYPE_CHECK_INSTANCE_CAST((obj), GST_TYPE_DREAM_OVERLAY_COMPOSITOR, GstDreamOverlayCompositor))

typedef struct _GstDreamOverlayCompositor GstDreamOverlayCompositor;
typedef struct _GstDreamOverlayCompositorClass GstDreamOverlayCompositorClass;
typedef struct _DreamOverlayPrivate DreamOverlayPrivate;

typedef struct {
	gint x, y, width, height;
} DreamOverlayRectangle;

struct _GstDreamOverlayCompositor {
	GstObject parent;
	/* Original host contract: dimensions immediately follow GstObject.
	 * Read-only. Surface rows are padded to a multiple of 256 bytes. */
	GstVideoInfo *vinfo;
	DreamOverlayPrivate *priv;
};

struct _GstDreamOverlayCompositorClass {
	GstObjectClass parent;
	void (*haveOverlay)(GstDreamOverlayCompositor *, gpointer);
	void (*setPointer)(GstDreamOverlayCompositor *, gpointer);
};

GType gst_dream_overlay_compositor_get_type(void);
GstDreamOverlayCompositor *gst_dream_overlay_compositor_new(void);
gboolean gst_dream_overlay_compositor_set_vinfo(GstDreamOverlayCompositor *compositor,
	const GstVideoInfo *info);
void gst_dream_overlay_compositor_set_timing(GstDreamOverlayCompositor *compositor,
	const GstSegment *segment, GstClockTime baseTime);
void gst_dream_overlay_compositor_set_paused(GstDreamOverlayCompositor *compositor,
	gboolean paused);
gboolean gst_dream_overlay_compositor_save_overlays(GstDreamOverlayCompositor *compositor,
	GstClock *clock, GstClockTime decoderTime, GstBuffer *buffer);
void gst_dream_overlay_compositor_free_overlays_stop_syncers(GstDreamOverlayCompositor *compositor);
/* Takes ownership of caps and returns plain + overlay-feature alternatives. */
GstCaps *gst_dream_overlay_compositor_add_caps(GstCaps *caps);

/* have-overlay receives a transferred GList of DreamOverlayRectangle*, or
 * NULL for a full clear. Exactly one host handler owns/frees that list.
 * set-pointer receives borrowed surface bytes, NOT a gSurface*. The host must
 * send NULL before releasing them. The call synchronizes with active drawing.
 * Resizing invalidates the old pointer; rebind storage using the new vinfo.
 * Lock order is private recursive lock, then inherited GstObject lock. Hosts
 * may hold GST_OBJECT_LOCK to snapshot pixels/vinfo, but must release it before
 * emitting actions. Host signal callbacks run without that inherited lock. */
void dream_overlay_rectangles_free(GList *rectangles);

G_END_DECLS
#endif
