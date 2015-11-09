require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

PR = "0"

SRC_URI = "${KERNELORG_MIRROR}/linux/kernel/v4.x/linux-${PV}.tar.xz"

SRC_URI[md5sum] = "58b35794eee3b6d52ce7be39357801e7"
SRC_URI[sha256sum] = "4a622cc84b8a3c38d39bc17195b0c064d2b46945dfde0dae18f77b120bc9f3ae"
