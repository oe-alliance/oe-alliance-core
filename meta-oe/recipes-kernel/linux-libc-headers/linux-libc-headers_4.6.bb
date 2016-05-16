require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

SRC_URI = "${KERNELORG_MIRROR}/linux/kernel/v4.x/linux-${PV}.tar.xz \
    file://fsxattr.patch \
"

SRC_URI[md5sum] = "d2927020e24a76da4ab482a8bc3e9ef3"
SRC_URI[sha256sum] = "a93771cd5a8ad27798f22e9240538dfea48d3a2bf2a6a6ab415de3f02d25d866"
