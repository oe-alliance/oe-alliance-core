DESCRIPTION = "gstplayer2 by samsamsam"
SECTION = "multimedia"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"

DEPENDS = "gstreamer1.0 gstreamer1.0-plugins-base"

inherit pkgconfig

SRCREV = "${AUTOREV}"

SRC_URI = "git://github.com/oe-mirrors/iptvplayer-bin-components.git;protocol=http;branch=master"

do_compile() {
    cd ${S}/gstplayer/gst-1.0
    ${CC} *.c ../common/*.c -I../common/ `pkg-config --cflags --libs gstreamer-1.0 gstreamer-pbutils-1.0` -o gstplayer2 ${LDFLAGS}
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/gstplayer/gst-1.0/gstplayer2 ${D}${bindir}/gstplayer2
}
