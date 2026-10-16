/* SPDX-License-Identifier: LGPL-2.1-or-later */
#include "dream_overlay.h"

#include <string.h>

#define MAX_OVERLAY_JOBS 128
#define MAX_OVERLAY_RECTANGLES 256
#define MAX_SURFACE_BYTES ((gsize) 256 * 1024 * 1024)
#define MAX_OVERLAY_DELAY (30 * GST_SECOND)
/* Observed original compensation for host surface presentation latency. */
#define OVERLAY_RENDER_LEAD (50 * GST_MSECOND)

GST_DEBUG_CATEGORY_STATIC(dream_overlay_debug);
#define GST_CAT_DEFAULT dream_overlay_debug

typedef struct {
	gint references;
	GstDreamOverlayCompositor *owner;
	GstVideoOverlayComposition *composition;
	GstClock *clock;
	GstClockID clockId;
	GstClockTime target, remaining;
	guint64 generation;
	gboolean listed;
} DreamOverlayJob;

struct _DreamOverlayPrivate {
	GRecMutex mutex;
	guint8 *surface;
	gint width, height;
	GList *jobs;
	GstVideoOverlayComposition *displayed, *submitted;
	guint32 submittedSequence;
	guint64 generation;
	GstSegment segment;
	GstClockTime baseTime, lastTarget;
	gboolean failed, paused;
};

enum { SIGNAL_HAVE_OVERLAY, SIGNAL_SET_POINTER, SIGNAL_COUNT };
static guint signals[SIGNAL_COUNT];

G_DEFINE_TYPE(GstDreamOverlayCompositor, gst_dream_overlay_compositor, GST_TYPE_OBJECT)

void dream_overlay_rectangles_free(GList *rectangles)
{
	g_list_free_full(rectangles, g_free);
}

static void replace_composition(GstVideoOverlayComposition **target,
	GstVideoOverlayComposition *composition)
{
	if (composition)
		gst_video_overlay_composition_ref(composition);
	if (*target)
		gst_video_overlay_composition_unref(*target);
	*target = composition;
}

/* Caller holds the recursive lock, allowing a host to detach its surface
 * synchronously from a have-overlay handler without deadlocking. */
static void emit_rectangles(GstDreamOverlayCompositor *self, GList *rectangles)
{
	if (g_signal_has_handler_pending(self, signals[SIGNAL_HAVE_OVERLAY], 0, TRUE))
		g_signal_emit(self, signals[SIGNAL_HAVE_OVERLAY], 0, rectangles);
	else
		dream_overlay_rectangles_free(rectangles);
}

static gboolean clip_rectangle(GstDreamOverlayCompositor *self,
	GstVideoOverlayRectangle *rectangle, DreamOverlayRectangle *clipped,
	guint *sourceX, guint *sourceY)
{
	gint x, y;
	guint width, height;
	gint64 right, bottom;
	gst_video_overlay_rectangle_get_render_rectangle(rectangle, &x, &y, &width, &height);
	right = MIN((gint64) x + width, self->priv->width);
	bottom = MIN((gint64) y + height, self->priv->height);
	clipped->x = MAX(x, 0);
	clipped->y = MAX(y, 0);
	if (right <= clipped->x || bottom <= clipped->y)
		return FALSE;
	clipped->width = (gint) (right - clipped->x);
	clipped->height = (gint) (bottom - clipped->y);
	*sourceX = (guint) ((gint64) clipped->x - x);
	*sourceY = (guint) ((gint64) clipped->y - y);
	return TRUE;
}

static GList *append_dirty(GstDreamOverlayCompositor *self, GList *dirty,
	GstVideoOverlayComposition *composition)
{
	if (composition) {
		guint count = gst_video_overlay_composition_n_rectangles(composition);
		for (guint index = 0; index < count; index++) {
			DreamOverlayRectangle clipped;
			guint sourceX, sourceY;
			if (clip_rectangle(self, gst_video_overlay_composition_get_rectangle(composition, index),
				&clipped, &sourceX, &sourceY)) {
				DreamOverlayRectangle *rectangle = g_new(DreamOverlayRectangle, 1);
				*rectangle = clipped;
				dirty = g_list_prepend(dirty, rectangle);
			}
		}
	}
	return dirty;
}

static gboolean draw_composition_unlocked(GstDreamOverlayCompositor *self,
	GstVideoOverlayComposition *composition)
{
	DreamOverlayPrivate *priv = self->priv;
	if (!priv->surface || !self->vinfo->size)
		return TRUE;
	memset(priv->surface, 0, self->vinfo->size);
	if (!composition)
		return TRUE;
	guint count = gst_video_overlay_composition_n_rectangles(composition);
	for (guint index = 0; index < count; index++) {
		GstVideoOverlayRectangle *rectangle = gst_video_overlay_composition_get_rectangle(composition, index);
		DreamOverlayRectangle clipped;
		guint sourceX, sourceY, width, height;
		gint x, y;
		if (!clip_rectangle(self, rectangle, &clipped, &sourceX, &sourceY))
			continue;
		/* Bound the scaled allocation before asking GStreamer to produce it. */
		gst_video_overlay_rectangle_get_render_rectangle(rectangle, &x, &y, &width, &height);
		if (width > 8192 || height > 8192 || (guint64) width * height * 4 > MAX_SURFACE_BYTES)
			return FALSE;
		gfloat alpha = gst_video_overlay_rectangle_get_global_alpha(rectangle);
		if (!(alpha >= 0.0f && alpha <= 1.0f))
			return FALSE;
		/* Request un-applied global alpha. Some GStreamer versions otherwise
		 * try to update the shared rectangle's alpha and fail writability checks.
		 * Multiply our destination only; upstream pixels remain read-only. */
		GstBuffer *pixels = gst_video_overlay_rectangle_get_pixels_argb(rectangle,
			GST_VIDEO_OVERLAY_FORMAT_FLAG_PREMULTIPLIED_ALPHA | GST_VIDEO_OVERLAY_FORMAT_FLAG_GLOBAL_ALPHA);
		GstVideoMeta *meta = pixels ? gst_buffer_get_video_meta(pixels) : NULL;
		GstMapInfo map = GST_MAP_INFO_INIT;
		if (!meta || meta->n_planes != 1 || meta->stride[0] <= 0 ||
			(guint64) sourceX + clipped.width > meta->width ||
			(guint64) sourceY + clipped.height > meta->height ||
			!gst_buffer_map(pixels, &map, GST_MAP_READ))
			return FALSE;
		guint64 offset = (guint64) meta->offset[0] + (guint64) sourceY * meta->stride[0] + (guint64) sourceX * 4;
		guint64 end = offset + (guint64) (clipped.height - 1) * meta->stride[0] + (guint64) clipped.width * 4;
		if (end > map.size || (guint64) meta->stride[0] < (guint64) meta->width * 4) {
			gst_buffer_unmap(pixels, &map);
			return FALSE;
		}
		for (gint row = 0; row < clipped.height; row++) {
			guint8 *destination = priv->surface + (gsize) (clipped.y + row) * self->vinfo->stride[0] + (gsize) clipped.x * 4;
			const guint8 *source = map.data + offset + (gsize) row * meta->stride[0];
			gsize rowBytes = (gsize) clipped.width * 4;
			if (alpha == 1.0f)
				memcpy(destination, source, rowBytes);
			else
				for (gsize byte = 0; byte < rowBytes; byte++)
					destination[byte] = (guint8) (source[byte] * alpha);
		}
		gst_buffer_unmap(pixels, &map);
	}
	return TRUE;
}

static gboolean draw_composition(GstDreamOverlayCompositor *self,
	GstVideoOverlayComposition *composition)
{
	/* Legacy hosts can use the inherited GstObject lock to snapshot the
	 * public video info and surface. Never hold it during host callbacks. */
	GST_OBJECT_LOCK(self);
	gboolean result = draw_composition_unlocked(self, composition);
	GST_OBJECT_UNLOCK(self);
	return result;
}

static void job_unref(gpointer data)
{
	DreamOverlayJob *job = data;
	if (g_atomic_int_dec_and_test(&job->references)) {
		if (job->composition)
			gst_video_overlay_composition_unref(job->composition);
		gst_object_unref(job->clock);
		gst_object_unref(job->owner);
		g_free(job);
	}
}

static DreamOverlayJob *job_ref(DreamOverlayJob *job)
{
	g_atomic_int_inc(&job->references);
	return job;
}

/* The pending list owns the ID reference; the ID's destroy notifier owns a
 * job reference. Releasing these independently avoids an ID/job ref cycle. */
static void release_clock_id(DreamOverlayJob *job, gboolean unschedule)
{
	GstClockID id = job->clockId;
	job->clockId = NULL;
	if (id) {
		if (unschedule)
			gst_clock_id_unschedule(id);
		gst_clock_id_unref(id);
	}
}

static gboolean draw_scheduled(GstClock *clock, GstClockTime time,
	GstClockID clockId, gpointer data)
{
	DreamOverlayJob *job = data;
	GstDreamOverlayCompositor *self = job->owner;
	DreamOverlayPrivate *priv = self->priv;
	(void) clock;
	(void) time;
	g_rec_mutex_lock(&priv->mutex);
	if (!job->listed || job->clockId != clockId || priv->paused) {
		g_rec_mutex_unlock(&priv->mutex);
		return TRUE;
	}
	/* Keep the list's ID reference until after the callback has finished. */
	job->clockId = NULL;
	if (job->generation == priv->generation && !priv->failed) {
		GList *dirty = append_dirty(self, NULL, priv->displayed);
		dirty = append_dirty(self, dirty, job->composition);
		if (!draw_composition(self, job->composition)) {
			priv->failed = TRUE;
			GST_ERROR_OBJECT(self, "Cannot map or validate overlay pixels");
			dream_overlay_rectangles_free(dirty);
		} else {
			replace_composition(&priv->displayed, job->composition);
			if (priv->surface)
				emit_rectangles(self, g_list_reverse(dirty));
			else
				dream_overlay_rectangles_free(dirty);
		}
	}
	if (job->listed) {
		priv->jobs = g_list_remove(priv->jobs, job);
		job->listed = FALSE;
		job_unref(job);
	}
	g_rec_mutex_unlock(&priv->mutex);
	gst_clock_id_unref(clockId);
	return TRUE;
}

static gboolean schedule_job(DreamOverlayJob *job)
{
	DreamOverlayPrivate *priv = job->owner->priv;
	if (priv->paused) {
		GstClockTime now = gst_clock_get_time(job->clock);
		job->remaining = job->target > now ? job->target - now : 0;
		return TRUE;
	}
	if (!GST_CLOCK_GET_CLASS(job->clock)->wait_async)
		return FALSE;
	job->clockId = gst_clock_new_single_shot_id(job->clock, job->target);
	if (!job->clockId)
		return FALSE;
	GstClockReturn result = gst_clock_id_wait_async(job->clockId, draw_scheduled,
		job_ref(job), job_unref);
	if (result != GST_CLOCK_OK) {
		release_clock_id(job, TRUE);
		return FALSE;
	}
	return TRUE;
}

static void cancel_jobs(GstDreamOverlayCompositor *self)
{
	DreamOverlayPrivate *priv = self->priv;
	++priv->generation;
	while (priv->jobs) {
		DreamOverlayJob *job = priv->jobs->data;
		priv->jobs = g_list_delete_link(priv->jobs, priv->jobs);
		job->listed = FALSE;
		release_clock_id(job, TRUE);
		job_unref(job);
	}
	priv->lastTarget = GST_CLOCK_TIME_NONE;
	replace_composition(&priv->submitted, NULL);
	priv->submittedSequence = 0;
}

void gst_dream_overlay_compositor_set_paused(GstDreamOverlayCompositor *self,
	gboolean paused)
{
	DreamOverlayPrivate *priv = self->priv;
	g_rec_mutex_lock(&priv->mutex);
	if (priv->paused != paused) {
		priv->paused = paused;
		if (paused) {
			for (GList *item = priv->jobs; item; item = item->next) {
				DreamOverlayJob *job = item->data;
				GstClockTime now = gst_clock_get_time(job->clock);
				job->remaining = job->target > now ? job->target - now : 0;
				release_clock_id(job, TRUE);
			}
		} else {
			/* A clock may call back synchronously and a host handler may clear
			 * the compositor. Use independent references while rescheduling. */
			GPtrArray *pending = g_ptr_array_new_with_free_func(job_unref);
			for (GList *item = priv->jobs; item; item = item->next)
				g_ptr_array_add(pending, job_ref(item->data));
			priv->lastTarget = GST_CLOCK_TIME_NONE;
			for (guint index = 0; index < pending->len; index++) {
				DreamOverlayJob *job = g_ptr_array_index(pending, index);
				if (!job->listed)
					continue;
				GstClockTime now = gst_clock_get_time(job->clock);
				if (job->remaining >= GST_CLOCK_TIME_NONE - now) {
					priv->failed = TRUE;
					break;
				}
				job->target = now + job->remaining;
				if (!schedule_job(job)) {
					priv->failed = TRUE;
					break;
				}
				priv->lastTarget = GST_CLOCK_TIME_IS_VALID(priv->lastTarget) ?
					MAX(priv->lastTarget, job->target) : job->target;
			}
			if (priv->failed)
				cancel_jobs(self);
			g_ptr_array_unref(pending);
		}
	}
	g_rec_mutex_unlock(&priv->mutex);
}

void gst_dream_overlay_compositor_free_overlays_stop_syncers(GstDreamOverlayCompositor *self)
{
	DreamOverlayPrivate *priv = self->priv;
	g_rec_mutex_lock(&priv->mutex);
	cancel_jobs(self);
	replace_composition(&priv->displayed, NULL);
	if (priv->surface) {
		GST_OBJECT_LOCK(self);
		memset(priv->surface, 0, self->vinfo->size);
		GST_OBJECT_UNLOCK(self);
		emit_rectangles(self, NULL);
	}
	priv->failed = FALSE;
	g_rec_mutex_unlock(&priv->mutex);
}

static void set_pointer(GstDreamOverlayCompositor *self, gpointer surface)
{
	DreamOverlayPrivate *priv = self->priv;
	g_rec_mutex_lock(&priv->mutex);
	priv->surface = surface;
	if (surface && !draw_composition(self, priv->displayed)) {
		priv->failed = TRUE;
		GST_ERROR_OBJECT(self, "Cannot render overlay on the supplied surface");
	}
	g_rec_mutex_unlock(&priv->mutex);
}

gboolean gst_dream_overlay_compositor_set_vinfo(GstDreamOverlayCompositor *self,
	const GstVideoInfo *info)
{
	gint width = GST_VIDEO_INFO_WIDTH(info), height = GST_VIDEO_INFO_HEIGHT(info);
	gsize stride;
	if (width <= 0 || width > 8192 || height <= 0 || height > 8192)
		return FALSE;
	stride = ((gsize) width * 4 + 255) & ~(gsize) 255;
	if (stride * height > MAX_SURFACE_BYTES)
		return FALSE;
	DreamOverlayPrivate *priv = self->priv;
	g_rec_mutex_lock(&priv->mutex);
	if (priv->width != width || priv->height != height) {
		gst_dream_overlay_compositor_free_overlays_stop_syncers(self);
		/* Never draw a new resolution into an old, potentially smaller buffer. */
		priv->surface = NULL;
		priv->width = width;
		priv->height = height;
	}
	/* Preserve negotiated format/PAR/rate fields as the original host contract
	 * does. Only its surface width, first-plane stride and byte size differ. */
	GST_OBJECT_LOCK(self);
	*self->vinfo = *info;
	self->vinfo->width = stride / 4;
	self->vinfo->stride[0] = stride;
	self->vinfo->size = stride * height;
	GST_OBJECT_UNLOCK(self);
	g_rec_mutex_unlock(&priv->mutex);
	return TRUE;
}

void gst_dream_overlay_compositor_set_timing(GstDreamOverlayCompositor *self,
	const GstSegment *segment, GstClockTime baseTime)
{
	g_rec_mutex_lock(&self->priv->mutex);
	self->priv->segment = *segment;
	self->priv->baseTime = baseTime;
	g_rec_mutex_unlock(&self->priv->mutex);
}

gboolean gst_dream_overlay_compositor_save_overlays(GstDreamOverlayCompositor *self,
	GstClock *clock, GstClockTime decoderTime, GstBuffer *buffer)
{
	DreamOverlayPrivate *priv = self->priv;
	GstVideoOverlayCompositionMeta *meta = gst_buffer_get_video_overlay_composition_meta(buffer);
	GstVideoOverlayComposition *composition = meta ? meta->overlay : NULL;
	guint32 sequence = composition ? gst_video_overlay_composition_get_seqnum(composition) : 0;
	GstClockTime pts = GST_BUFFER_PTS(buffer), now, target, running;
	if (!GST_CLOCK_TIME_IS_VALID(pts))
		pts = GST_BUFFER_DTS(buffer);
	if (composition && gst_video_overlay_composition_n_rectangles(composition) > MAX_OVERLAY_RECTANGLES)
		return FALSE;
	g_rec_mutex_lock(&priv->mutex);
	if (priv->failed) {
		g_rec_mutex_unlock(&priv->mutex);
		return FALSE;
	}
	if (composition == priv->submitted && sequence == priv->submittedSequence) {
		g_rec_mutex_unlock(&priv->mutex);
		return TRUE;
	}
	if (!clock || !GST_CLOCK_TIME_IS_VALID(pts) || !self->vinfo->size) {
		GST_WARNING_OBJECT(self, "Cannot schedule overlay without dimensions, a clock and timestamp");
		g_rec_mutex_unlock(&priv->mutex);
		return FALSE;
	}
	if (g_list_length(priv->jobs) >= MAX_OVERLAY_JOBS) {
		GST_ERROR_OBJECT(self, "Overlay scheduling queue limit reached");
		g_rec_mutex_unlock(&priv->mutex);
		return FALSE;
	}
	now = gst_clock_get_time(clock);
	running = gst_segment_to_running_time(&priv->segment, GST_FORMAT_TIME, pts);
	if (!GST_CLOCK_TIME_IS_VALID(now) || !GST_CLOCK_TIME_IS_VALID(running)) {
		GST_WARNING_OBJECT(self, "Overlay timestamp cannot be converted to running time");
		g_rec_mutex_unlock(&priv->mutex);
		return FALSE;
	}
	GstClockTime decoderRunning = GST_CLOCK_TIME_IS_VALID(decoderTime) ?
		gst_segment_to_running_time(&priv->segment, GST_FORMAT_TIME, decoderTime) : GST_CLOCK_TIME_NONE;
	target = now;
	if (GST_CLOCK_TIME_IS_VALID(running) && GST_CLOCK_TIME_IS_VALID(decoderRunning)) {
		if (running > decoderRunning) {
			GstClockTime delay = running - decoderRunning;
			if (delay > MAX_OVERLAY_DELAY || delay >= GST_CLOCK_TIME_NONE - now) {
				GST_ERROR_OBJECT(self, "Overlay decoder timestamp is outside the scheduling window");
				g_rec_mutex_unlock(&priv->mutex);
				return FALSE;
			}
			target += delay;
		}
	} else if (GST_CLOCK_TIME_IS_VALID(running) && GST_CLOCK_TIME_IS_VALID(priv->baseTime) &&
		running < GST_CLOCK_TIME_NONE - priv->baseTime) {
		target = MAX(now, priv->baseTime + running);
	}
	if (target - now > MAX_OVERLAY_DELAY) {
		GST_ERROR_OBJECT(self, "Overlay timestamp is outside the scheduling window");
		g_rec_mutex_unlock(&priv->mutex);
		return FALSE;
	}
	target = target - now > OVERLAY_RENDER_LEAD ? target - OVERLAY_RENDER_LEAD : now;
	if (GST_CLOCK_TIME_IS_VALID(priv->lastTarget) && target <= priv->lastTarget) {
		if (priv->lastTarget == GST_CLOCK_TIME_NONE - 1) {
			g_rec_mutex_unlock(&priv->mutex);
			return FALSE;
		}
		/* Equal clock deadlines need not execute in insertion order. */
		target = priv->lastTarget + 1;
	}
	DreamOverlayJob *job = g_try_new0(DreamOverlayJob, 1);
	if (!job) {
		g_rec_mutex_unlock(&priv->mutex);
		return FALSE;
	}
	job->references = 2; /* Pending list and this submission's local reference. */
	job->owner = gst_object_ref(self);
	job->clock = gst_object_ref(clock);
	job->composition = composition ? gst_video_overlay_composition_ref(composition) : NULL;
	job->generation = priv->generation;
	job->target = target;
	job->listed = TRUE;
	priv->jobs = g_list_append(priv->jobs, job);
	if (!schedule_job(job)) {
		if (job->listed) {
			priv->jobs = g_list_remove(priv->jobs, job);
			job->listed = FALSE;
			job_unref(job);
		}
		job_unref(job);
		g_rec_mutex_unlock(&priv->mutex);
		return FALSE;
	}
	if (job->generation == priv->generation) {
		replace_composition(&priv->submitted, composition);
		priv->submittedSequence = sequence;
		priv->lastTarget = target;
	}
	job_unref(job);
	g_rec_mutex_unlock(&priv->mutex);
	return TRUE;
}

GstCaps *gst_dream_overlay_compositor_add_caps(GstCaps *caps)
{
	GstCaps *overlay = gst_caps_copy(caps);
	GstCaps *explicitMemory = gst_caps_copy(caps);
	for (guint index = 0; index < gst_caps_get_size(overlay); index++) {
		GstCapsFeatures *features = gst_caps_get_features(overlay, index);
		/* Generic overlay elements use meta-only features for system memory.
		 * Feature matching is exact; also retain the original explicit-memory
		 * variant for callers which include memory:SystemMemory themselves. */
		gst_caps_features_remove(features, GST_CAPS_FEATURE_MEMORY_SYSTEM_MEMORY);
		gst_caps_features_add(features, GST_CAPS_FEATURE_META_GST_VIDEO_OVERLAY_COMPOSITION);
		gst_caps_features_add(gst_caps_get_features(explicitMemory, index),
			GST_CAPS_FEATURE_META_GST_VIDEO_OVERLAY_COMPOSITION);
	}
	return gst_caps_merge(gst_caps_merge(overlay, explicitMemory), caps);
}

static void overlay_finalize(GObject *object)
{
	GstDreamOverlayCompositor *self = GST_DREAM_OVERLAY_COMPOSITOR(object);
	/* Pending clock jobs hold a reference. The sink cancels them at stop. */
	/* stop/flush clears the borrowed surface while the host is alive. Never
	 * emit application callbacks from an object whose refcount reached zero. */
	self->priv->surface = NULL;
	cancel_jobs(self);
	replace_composition(&self->priv->displayed, NULL);
	g_rec_mutex_clear(&self->priv->mutex);
	g_free(self->priv);
	gst_video_info_free(self->vinfo);
	G_OBJECT_CLASS(gst_dream_overlay_compositor_parent_class)->finalize(object);
}

static void gst_dream_overlay_compositor_class_init(GstDreamOverlayCompositorClass *klass)
{
	G_OBJECT_CLASS(klass)->finalize = overlay_finalize;
	klass->setPointer = set_pointer;
	signals[SIGNAL_HAVE_OVERLAY] = g_signal_new("have-overlay", G_TYPE_FROM_CLASS(klass), G_SIGNAL_RUN_LAST,
		G_STRUCT_OFFSET(GstDreamOverlayCompositorClass, haveOverlay), NULL, NULL,
		g_cclosure_marshal_VOID__POINTER, G_TYPE_NONE, 1, G_TYPE_POINTER);
	signals[SIGNAL_SET_POINTER] = g_signal_new("set-pointer", G_TYPE_FROM_CLASS(klass), G_SIGNAL_RUN_LAST | G_SIGNAL_ACTION,
		G_STRUCT_OFFSET(GstDreamOverlayCompositorClass, setPointer), NULL, NULL,
		g_cclosure_marshal_VOID__POINTER, G_TYPE_NONE, 1, G_TYPE_POINTER);
	GST_DEBUG_CATEGORY_INIT(dream_overlay_debug, "dreamoverlaycompositor", 0, "Dream video overlays");
}

static void gst_dream_overlay_compositor_init(GstDreamOverlayCompositor *self)
{
	self->vinfo = gst_video_info_new();
	self->priv = g_new0(DreamOverlayPrivate, 1);
	g_rec_mutex_init(&self->priv->mutex);
	gst_segment_init(&self->priv->segment, GST_FORMAT_TIME);
	self->priv->baseTime = self->priv->lastTarget = GST_CLOCK_TIME_NONE;
	self->priv->paused = TRUE;
}

GstDreamOverlayCompositor *gst_dream_overlay_compositor_new(void)
{
	return g_object_new(GST_TYPE_DREAM_OVERLAY_COMPOSITOR, NULL);
}
