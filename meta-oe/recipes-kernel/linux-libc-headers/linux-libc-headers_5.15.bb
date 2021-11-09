require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

DEPENDS += "flex-native bison-native"

SRC_URI = "${KERNELORG_MIRROR}/linux/kernel/v5.x/linux-${PV}.tar.xz \
    file://dmx_set_source.patch \
    file://audio_video_ioctl.patch \
"

SRC_URI[md5sum] = "071d49ff4e020d58c04f9f3f76d3b594"
SRC_URI[sha256sum] = "57b2cf6991910e3b67a1b3490022e8a0674b6965c74c12da1e99d138d1991ee8"
