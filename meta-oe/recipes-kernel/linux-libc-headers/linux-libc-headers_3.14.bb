require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

PR = "1"

SRC_URI = "${KERNELORG_MIRROR}/linux/kernel/v${HEADER_FETCH_VER}/linux-${PV}.tar.xz"

SRC_URI[md5sum] = "b621207b3f6ecbb67db18b13258f8ea8"
SRC_URI[sha256sum] = "61558aa490855f42b6340d1a1596be47454909629327c49a5e4e10268065dffa"
