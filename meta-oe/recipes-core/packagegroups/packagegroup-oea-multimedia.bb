SUMMARY = "OE-Alliance Multimedia - GStreamer, codecs, and media players"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

inherit packagegroup

ALLOW_EMPTY:${PN} = "1"

RDEPENDS:${PN} = "\
    ${@bb.utils.contains("MACHINE_FEATURES", "smallflash", "", "${OEA_CORE_EXTENDED}", d)} \
    "

OEA_CORE_EXTENDED = "\
    gstreamer1.0-plugin-subsink \
    ${GST_BASE_RDEPS} \
    ${GST_GOOD_RDEPS} \
    ${GST_BAD_RDEPS} \
    ${GST_UGLY_RDEPS} \
    ${GST_BAD_OPUS} \
    ffmpeg \
    "

GST_BASE_RDEPS = "\
    gstreamer1.0-plugins-base-alsa \
    gstreamer1.0-plugins-base-app \
    gstreamer1.0-plugins-base-audioconvert \
    gstreamer1.0-plugins-base-audioresample \
    gstreamer1.0-plugins-base-audiorate \
    gstreamer1.0-plugins-base-videoconvertscale \
    gstreamer1.0-plugins-base-ivorbisdec \
    gstreamer1.0-plugins-base-ogg \
    gstreamer1.0-plugins-base-playback \
    gstreamer1.0-plugins-base-subparse \
    gstreamer1.0-plugins-base-typefindfunctions \
    gstreamer1.0-plugins-base-vorbis \
    gstreamer1.0-plugins-base-rawparse \
    "

GST_GOOD_RDEPS = "\
    gstreamer1.0-plugins-good-amrnb \
    gstreamer1.0-plugins-good-amrwbdec \
    gstreamer1.0-plugins-good-apetag \
    gstreamer1.0-plugins-good-audioparsers \
    gstreamer1.0-plugins-good-autodetect \
    gstreamer1.0-plugins-good-avi \
    gstreamer1.0-plugins-good-flac \
    gstreamer1.0-plugins-good-flv \
    gstreamer1.0-plugins-good-icydemux \
    gstreamer1.0-plugins-good-id3demux \
    gstreamer1.0-plugins-good-isomp4 \
    gstreamer1.0-plugins-good-matroska \
    gstreamer1.0-plugins-good-rtp \
    gstreamer1.0-plugins-good-rtpmanager \
    gstreamer1.0-plugins-good-rtsp \
    gstreamer1.0-plugins-good-soup \
    gstreamer1.0-plugins-good-udp \
    gstreamer1.0-plugins-good-wavparse \
    gstreamer1.0-plugins-good-wavpack \
    "

GST_BAD_RDEPS = "\
    gstreamer1.0-plugins-bad-dash \
    gstreamer1.0-plugins-bad-mpegpsdemux \
    gstreamer1.0-plugins-bad-mpegtsdemux \
    gstreamer1.0-plugins-bad-rtmp \
    gstreamer1.0-plugins-bad-smoothstreaming \
    gstreamer1.0-plugins-bad-faad \
    gstreamer1.0-plugins-bad-hls \
    gstreamer1.0-plugins-bad-videoparsersbad \
    gstreamer1.0-plugins-bad-autoconvert \
    "

GST_UGLY_RDEPS = "\
    gstreamer1.0-plugins-ugly-asf \
    gstreamer1.0-plugins-ugly-cdio \
    gstreamer1.0-plugins-ugly-dvdsub \
    "

GST_BAD_OPUS = "\
    ${@bb.utils.contains("TARGET_ARCH", "arm", "gstreamer1.0-plugins-base-opus gstreamer1.0-plugins-bad-opusparse", "", d)} \
    ${@bb.utils.contains("TARGET_ARCH", "aarch64", "gstreamer1.0-plugins-base-opus gstreamer1.0-plugins-bad-opusparse", "", d)} \
    "

