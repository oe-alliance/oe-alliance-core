require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

PR = "1"

SRC_URI = "${KERNELORG_MIRROR}/linux/kernel/v4.x/linux-${PV}.tar.xz"

SRC_URI[md5sum] = "a86916bd12798220da9eb4a1eec3616d"
SRC_URI[sha256sum] = "0f2f7d44979bc8f71c4fc5d3308c03499c26a824dd311fdf6eef4dee0d7d5991"
