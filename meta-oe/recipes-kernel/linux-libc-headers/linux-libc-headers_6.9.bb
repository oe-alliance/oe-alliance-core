require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

DEPENDS += "flex-native bison-native"

SRC_URI = "${KERNELORG_MIRROR}/linux/kernel/v6.x/linux-${PV}.tar.xz \
    file://dmx_set_source.patch \
    file://audio_video_ioctl.patch \
"

SRC_URI[sha256sum] = "24fa01fb989c7a3e28453f117799168713766e119c5381dac30115f18f268149"
