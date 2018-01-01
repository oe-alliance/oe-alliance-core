DESCRIPTION = "A template for writing your own GStreamer plug-in"
MAINTAINER = "samsamsam"

DEPENDS = "gstreamer1.0 gstreamer1.0-plugins-base"

require conf/license/license-gplv2.inc
inherit gitpkgv autotools pkgconfig

PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r0"

SRC_URI = "git://gitlab.com/samsamsam/iptvplayer-bin-components.git;protocol=http"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git/gst-ifdsrc/gst-ifdsrc"

FILES_${PN} += "${libdir}/gstreamer-1.0"
