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
    assrender faac faad libde265 neon opusparse resindvd rtmp srt \
"

PACKAGECONFIG:remove = "rsvg"

PV = "1.24.8"
 
SRC_URI[md5sum] = "b0791b9671b875b25bb532f62edc6432"
SRC_URI[sha256sum] = "35ad70de3d7cbca3089f33bb77c45750daad2ae93d79827fdbb469fa8aba84eb"
