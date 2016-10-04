require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

SRC_URI = "${KERNELORG_MIRROR}/linux/kernel/v4.x/linux-${PV}.tar.xz \
    file://fsxattr.patch \
"

SRC_URI[md5sum] = "c1af0afbd3df35c1ccdc7a5118cd2d07"
SRC_URI[sha256sum] = "3e9150065f193d3d94bcf46a1fe9f033c7ef7122ab71d75a7fb5a2f0c9a7e11a"
