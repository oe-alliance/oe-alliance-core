FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI += " \
    file://dmx_set_source.patch \
    file://audio_video_ioctl.patch \
"
