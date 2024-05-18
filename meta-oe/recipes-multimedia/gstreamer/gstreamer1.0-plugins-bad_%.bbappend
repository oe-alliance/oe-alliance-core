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

PV = "1.22.12"
 
SRC_URI[md5sum] = "8873c3609a9b1b0742b92057c6803110"
SRC_URI[sha256sum] = "388b4c4412f42e36a38b17cc34119bc11879bd4d9fbd4ff6d03b2c7fc6b4d494"
