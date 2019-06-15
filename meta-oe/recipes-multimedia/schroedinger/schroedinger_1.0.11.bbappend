DEPENDS += "liboil alsa-lib"

PACKAGES =+ "gst-plugin-schroedinger-dbg gst-plugin-schroedinger-dev gst-plugin-schroedinger"

FILES_gst-plugin-schroedinger = "${libdir}/gstreamer-0.10/*.so"
FILES_gst-plugin-schroedinger-dbg = "${libdir}/gstreamer-0.10/.debug"
FILES_gst-plugin-schroedinger-dev = "${libdir}/gstreamer-0.10/*.*a"
