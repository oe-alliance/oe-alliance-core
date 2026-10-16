/* SPDX-License-Identifier: LGPL-2.1-or-later */
#ifdef HAVE_CONFIG_H
#include "config.h"
#endif
#include "gstdreamvideosink.h"

static gboolean plugin_init(GstPlugin *plugin)
{
	return gst_dream_video_sink_register(plugin);
}

GST_PLUGIN_DEFINE(GST_VERSION_MAJOR, GST_VERSION_MINOR, dreamvideosink,
	"Dreambox One/Two hardware video sink", plugin_init, PACKAGE_VERSION,
	"Proprietary", PACKAGE_NAME, "https://github.com/oe-alliance/oe-alliance-core")
