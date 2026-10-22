SUMMARY = "Internal GStreamer video sink candidate for Dreambox One and Two"
DESCRIPTION = "dreamvideosink uses the Dream decoder userspace interface and preserves the GStreamer interface consumed by Enigma2."
SECTION = "multimedia"
# Newly authored code is LGPL-2.1-or-later. The included Amlogic-derived H.263
# converter has restrictive terms: this combined candidate is internal only,
# not a redistributable LGPL release. Retain both notices and their provenance.
require conf/license/license-close.inc
LIC_FILES_CHKSUM = " \
    file://COPYING;md5=771782cb6245c7fbbe74bc0ec059beff \
    file://LICENSE.amlogic;md5=a0df61d14f0879e56c573c6938dd72f2 \
"

DEPENDS = "gstreamer1.0 gstreamer1.0-plugins-base gstreamer1.0-plugins-bad udev"
RDEPENDS:${PN} += "gstreamer1.0-plugins-bad-videoparsersbad"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "^(dreamone|dreamtwo)$"

SRC_URI = " \
    file://COPYING;subdir=${BP} \
    file://LICENSE.amlogic;subdir=${BP} \
    file://README.txt;subdir=${BP} \
    file://TESTING.txt;subdir=${BP} \
    file://docs/OVERLAYS.txt;subdir=${BP} \
    file://docs/CODECS.txt;subdir=${BP} \
    file://configure.ac;subdir=${BP} \
    file://Makefile.am;subdir=${BP} \
    file://src/Makefile.am;subdir=${BP} \
    file://src/plugin.c;subdir=${BP} \
    file://src/gstdreamvideosink.c;subdir=${BP} \
    file://src/gstdreamvideosink.h;subdir=${BP} \
    file://src/dream_video_device.c;subdir=${BP} \
    file://src/dream_video_device.h;subdir=${BP} \
    file://src/dream_codec.c;subdir=${BP} \
    file://src/dream_codec.h;subdir=${BP} \
    file://src/dream_h263.c;subdir=${BP} \
    file://src/dream_h263.h;subdir=${BP} \
    file://src/dream_overlay.c;subdir=${BP} \
    file://src/dream_overlay.h;subdir=${BP} \
    file://tests/Makefile.am;subdir=${BP} \
    file://tests/test_codec.c;subdir=${BP} \
    file://tests/test_device.c;subdir=${BP} \
    file://tests/test_sink.c;subdir=${BP} \
    file://tests/test_overlay.c;subdir=${BP} \
"

S = "${UNPACKDIR}/${BP}"

inherit autotools pkgconfig

EXTRA_OECONF = "--disable-tests"

# Both packages export the same GStreamer element factory. Keep an explicit
# package conflict instead of silently selecting whichever plugin loads first.
RCONFLICTS:${PN} = "gstreamer1.0-dreamsinks"

FILES:${PN} = "${libdir}/gstreamer-1.0/*.so"
FILES:${PN}-dev += "${libdir}/gstreamer-1.0/*.la"
FILES:${PN}-dbg += "${libdir}/gstreamer-1.0/.debug"
