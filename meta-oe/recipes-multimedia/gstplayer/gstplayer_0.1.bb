DESCRIPTION = "gstplayer by samsamsam"
SECTION = "multimedia"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

DEPENDS = "gstreamer1.0 gstreamer1.0-plugins-base"

SRCREV = "9f59b0476a4b3e0418eacdc775b866cdd841f190"
SRC_URI = "git://gitlab.com/samsamsam/iptvplayer-bin-components.git;protocol=http"
SRC_URI =+ "file://0001-set-iptv-download-timeout-0-to-disable-ifdsrc.patch \
            file://0002-use-getopt-added-possibility-to-set-custom-sinks.patch \
            file://0003-fix-buffer-duration-buffer-size-and-ring-buffer-size.patch \
            file://0004-rename-stored-sink-to-dvbSink-for-clarity.patch \
            file://0005-added-basic-support-for-plain-text-embedded-subtitle.patch \
            file://0006-allow-re-filling-of-audio-subtitle-tracks.patch \
            file://0007-re-enable-updating-video-track-info-from-dvb-video-d.patch \
            file://0008-first-flush-then-change-track.patch \
            file://0009-try-to-get-PTS-from-video-sink-first.patch \
            file://0010-fix-framerate-calculation.patch \
            file://0011-increase-eos-fix-timeout-to-10s.patch"

S = "${WORKDIR}/git/"

do_compile() {
    cd ${S}/gstplayer/gst-1.0
    ${CC} *.c ../common/*.c -I../common/ `pkg-config --cflags --libs gstreamer-1.0 gstreamer-pbutils-1.0` -o gstplayer_gst-1.0
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/gstplayer/gst-1.0/gstplayer_gst-1.0 ${D}${bindir}
}
