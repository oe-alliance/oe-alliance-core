require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

DEPENDS += "flex-native bison-native"

SRC_URI = "${KERNELORG_MIRROR}/linux/kernel/v5.x/linux-${PV}.tar.xz \
    file://fsxattr.patch \
    file://dmx_set_source.patch \
    file://audio_video_ioctl.patch \
"

SRC_URI[md5sum] = "753adc474bf799d569dec4f165ed92c3"
SRC_URI[sha256sum] = "dcdf99e43e98330d925016985bfbc7b83c66d367b714b2de0cbbfcbf83d8ca43"
