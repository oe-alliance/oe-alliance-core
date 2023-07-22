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
"

PACKAGECONFIG:append = " \
    assrender faac faad libde265 neon opusparse resindvd rtmp \
"

PACKAGECONFIG:remove = "rsvg"

PV = "1.22.5"

SRC_URI[md5sum] = "d18cde538d5c954e94f888143e703a52"
SRC_URI[sha256sum] = "e64e75cdafd7ff2fc7fc34e855b06b1e3ed227cc06fa378d17bbcd76780c338c"
