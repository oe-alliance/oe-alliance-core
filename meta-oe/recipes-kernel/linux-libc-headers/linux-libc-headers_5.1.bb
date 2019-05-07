require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

DEPENDS += "flex-native bison-native"

SRC_URI = "${KERNELORG_MIRROR}/linux/kernel/v5.x/linux-${PV}.tar.xz \
    file://fsxattr.patch \
    file://dmx_set_source.patch \
    file://audio_video_ioctl.patch \
"

SRC_URI[md5sum] = "15fbdff95ff98483069ac6e215b9f4f9"
SRC_URI[sha256sum] = "d06a7be6e73f97d1350677ad3bae0ce7daecb79c2c2902aaabe806f7fa94f041"
