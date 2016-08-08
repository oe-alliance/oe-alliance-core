require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

SRC_URI = "${KERNELORG_MIRROR}/linux/kernel/v4.x/linux-${PV}.tar.xz \
    file://fsxattr.patch \
"

SRC_URI[md5sum] = "5276563eb1f39a048e4a8a887408c031"
SRC_URI[sha256sum] = "5190c3d1209aeda04168145bf50569dc0984f80467159b1dc50ad731e3285f10"
