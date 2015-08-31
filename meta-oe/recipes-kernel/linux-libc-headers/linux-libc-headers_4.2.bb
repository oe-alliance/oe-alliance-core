require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

PR = "0"

SRC_URI = "${KERNELORG_MIRROR}/linux/kernel/v4.x/linux-${PV}.tar.xz"

SRC_URI[md5sum] = "3d5ea06d767e2f35c999eeadafc76523"
SRC_URI[sha256sum] = "cf20e044f17588d2a42c8f2a450b0fd84dfdbd579b489d93e9ab7d0e8b45dbeb"
