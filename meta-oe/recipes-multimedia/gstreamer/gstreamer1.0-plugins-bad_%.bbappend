FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

PACKAGE_NO_LOCALE = "1"
SRC_URI:append = " \
        file://0001-Revert-tsdemux-Limit-the-maximum-PES-payload-size.patch \
        file://0002-Revert-tsdemux-always-take-the-seek-segment-stop-int.patch \
        file://0003-Revert-tsdemux-Use-gst_segment_do_seek.patch \
        file://0004-rtmp-hls-tsdemux-fix.patch \
        file://0005-rtmp-fix-seeking-and-potential-segfault.patch \
        file://0006-dvbapi5-fix-old-kernel.patch \
        file://0007-hls-main-thread-block.patch \
        file://0008-allow-building-against-newer-neon.patch \
"

PACKAGECONFIG:append = " \
    assrender faac faad libde265 neon opusparse resindvd rtmp \
"

PACKAGECONFIG:remove = "rsvg"

PV = "1.22.9"

SRC_URI[md5sum] = "46da4d6a2184d4e6af63e7594a80be0a"
SRC_URI[sha256sum] = "1bc65d0fd5f53a3636564efd3fcf318c3edcdec39c4109a503c1fc8203840a1d"
