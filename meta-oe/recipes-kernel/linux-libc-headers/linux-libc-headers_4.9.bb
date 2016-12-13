require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

SRC_URI = "${KERNELORG_MIRROR}/linux/kernel/v4.x/linux-${PV}.tar.xz \
    file://fsxattr.patch \
"

SRC_URI[md5sum] = "0a68ef3615c64bd5ee54a3320e46667d"
SRC_URI[sha256sum] = "029098dcffab74875e086ae970e3828456838da6e0ba22ce3f64ef764f3d7f1a"
