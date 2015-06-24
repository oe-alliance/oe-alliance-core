require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

PR = "0"

SRC_URI = "${KERNELORG_MIRROR}/linux/kernel/v4.x/linux-${PV}.tar.xz"

SRC_URI[md5sum] = "fe9dc0f6729f36400ea81aa41d614c37"
SRC_URI[sha256sum] = "caf51f085aac1e1cea4d00dbbf3093ead07b551fc07b31b2a989c05f8ea72d9f"
