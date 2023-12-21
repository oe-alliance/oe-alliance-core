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

PV = "1.22.8"

SRC_URI[md5sum] = "c1cd7068a8ab592e0d338ec2c1e2dab2"
SRC_URI[sha256sum] = "458783f8236068991e3e296edd671c8eddb8be6fac933c1c2e1503462864ea0f"
