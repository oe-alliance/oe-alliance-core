DESCRIPTION = "A template for writing your own GStreamer plug-in"
MAINTAINER = "samsamsam"
require conf/license/license-gplv2.inc

DEPENDS = "gstreamer1.0 gstreamer1.0-plugins-base"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "1.0+git"
PKGV = "1.0+git${GITPKGV}"
PR = "r1"

SRC_URI = "git://gitlab.com/samsamsam/iptvplayer-bin-components.git;protocol=https;branch=master"

S = "${WORKDIR}/git/gst-ifdsrc/gst-ifdsrc"

inherit autotools pkgconfig

FILES:${PN} += "${libdir}/gstreamer-1.0"
