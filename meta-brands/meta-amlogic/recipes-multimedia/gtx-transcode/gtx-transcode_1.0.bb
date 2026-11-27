SUMMARY = "GTX hardware transcoding worker for the Enigma2 stream server"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"
COMPATIBLE_MACHINE = "^gtxcombo$"
PACKAGE_ARCH = "${MACHINE_ARCH}"
SRC_URI = "file://gtx-transcode.c file://gtx-sm1-decode.c"
S = "${UNPACKDIR}"
PR = "r4"
inherit pkgconfig
DEPENDS = "gstreamer1.0-plugins-base gtx-media-modules"
RDEPENDS:${PN} = "gtx-venc kernel-module-encoder kernel-module-vpu gstreamer1.0-libav gstreamer1.0-plugins-base-playback gstreamer1.0-plugins-base-videorate gstreamer1.0-plugins-base-videoconvertscale gstreamer1.0-plugins-base-audioconvert gstreamer1.0-plugins-base-audioresample gstreamer1.0-plugins-good-soup gstreamer1.0-plugins-bad-mpegtsdemux gstreamer1.0-plugins-bad-mpegtsmux gstreamer1.0-plugins-bad-videoparsersbad gstreamer1.0-plugins-good-audioparsers"

do_compile[depends] += "virtual/kernel:do_shared_workdir"

do_compile() {
    ${CC} ${CPPFLAGS} ${CFLAGS} -DGTX_VENC_DIR='"${libdir}/gtx-venc"' -idirafter ${STAGING_KERNEL_DIR}/common_drivers/include/uapi/amlogic ${S}/gtx-transcode.c ${S}/gtx-sm1-decode.c -o gtx-transcode ${LDFLAGS} $(pkg-config --cflags --libs gstreamer-video-1.0 glib-2.0) -ldl
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${B}/gtx-transcode ${D}${bindir}/
}
