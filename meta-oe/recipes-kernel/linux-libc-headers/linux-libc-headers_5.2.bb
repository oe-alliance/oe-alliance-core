require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

DEPENDS += "flex-native bison-native"

SRC_URI = "${KERNELORG_MIRROR}/linux/kernel/v5.x/linux-${PV}.tar.xz \
    file://fsxattr.patch \
    file://dmx_set_source.patch \
    file://audio_video_ioctl.patch \
"

SRC_URI[md5sum] = "ddf994de00d7b18395886dd9b30b9262"
SRC_URI[sha256sum] = "54ad66f672e1a831b574f5e704e8a05f1e6180a8245d4bdd811208a6cb0ac1e7"
