require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

DEPENDS += "flex-native bison-native"

SRC_URI = "${KERNELORG_MIRROR}/linux/kernel/v6.x/linux-${PV}.tar.xz \
    file://dmx_set_source.patch \
    file://audio_video_ioctl.patch \
"

SRC_URI[md5sum] = "bb65b2232cf596e7044c56a7c4205f51"
SRC_URI[sha256sum] = "8fa0588f0c2ceca44cac77a0e39ba48c9f00a6b9dc69761c02a5d3efac8da7f3"
