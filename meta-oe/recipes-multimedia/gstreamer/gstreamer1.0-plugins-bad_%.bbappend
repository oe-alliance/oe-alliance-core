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
        file://0008-gsthlsaudiometa.patch \
"

PACKAGECONFIG:append = " \
    assrender faac faad libde265 neon opusparse resindvd rtmp srt \
"

PACKAGECONFIG:remove = "rsvg"

PV = "1.26.8"
SRC_URI[sha256sum] = "25c05be5eb0694bde3ecd3317516f5d0d6e4ad21125a5ebcf6fb644a4c92439f"
