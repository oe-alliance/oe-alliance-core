require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

DEPENDS += "flex-native bison-native"

SRC_URI = "${KERNELORG_MIRROR}/linux/kernel/v6.x/linux-${PV}.tar.xz \
    file://dmx_set_source.patch \
    file://audio_video_ioctl.patch \
"

SRC_URI[md5sum] = "42c2796f9babe4a1be3f2001c817ff4a"
SRC_URI[sha256sum] = "7a574bbc20802ea76b52ca7faf07267f72045e861b18915c5272a98c27abf884"
