#ifdef HAVE_CONFIG_H
#include "config.h"
#endif

#include <gst/gst.h>
#include "gstdreamaudiosink.h"

static gboolean
plugin_init(GstPlugin *plugin)
{
    return gst_element_register(
        plugin, "dreamaudiosink", GST_RANK_PRIMARY,
        GST_TYPE_DREAM_AUDIO_SINK);
}

#ifndef PACKAGE
#define PACKAGE "gst-plugin-dreamaudiosink"
#endif
#ifndef VERSION
#define VERSION "0.1.0"
#endif

GST_PLUGIN_DEFINE(
    GST_VERSION_MAJOR,
    GST_VERSION_MINOR,
    dreamaudiosink,
    "Dreambox AMLogic audio sink",
    plugin_init,
    VERSION,
    "GPL",
    PACKAGE,
    "https://github.com/openatv/enigma2"
)
