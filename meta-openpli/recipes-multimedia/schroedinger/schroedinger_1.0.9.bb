require schroedinger.inc

PR = "${INC_PR}.0"

inherit autotools

SRC_URI[schroedingertargz.md5sum] = "d67ec48b7c506db8c8b49156bf409e60"
SRC_URI[schroedingertargz.sha256sum] = "345abcaa72ff0f2e9c1075e22f7141475ee4e6eea23a7f568b69ffc13cc1c723"

PACKAGES =+ "gst-plugin-schroedinger-dbg gst-plugin-schroedinger-dev gst-plugin-schroedinger"
FILES_gst-plugin-schroedinger = "${libdir}/gstreamer-0.10/*.so"
FILES_gst-plugin-schroedinger-dbg = "${libdir}/gstreamer-0.10/.debug"
FILES_gst-plugin-schroedinger-dev = "${libdir}/gstreamer-0.10/*.*a"
