DEPENDS += "liboil alsa-lib"

PACKAGES =+ "gst-plugin-schroedinger-dbg gst-plugin-schroedinger-dev gst-plugin-schroedinger"

FILES:gst-plugin-schroedinger = "${libdir}/gstreamer-0.10/*.so"
FILES:gst-plugin-schroedinger-dbg = "${libdir}/gstreamer-0.10/.debug"
FILES:gst-plugin-schroedinger-dev = "${libdir}/gstreamer-0.10/*.*a"
