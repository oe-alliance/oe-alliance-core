require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

PR = "1"

SRC_URI = "${KERNELORG_MIRROR}/linux/kernel/v${HEADER_FETCH_VER}/linux-${PV}.tar.xz"

SRC_URI[md5sum] = "0ecbaf65c00374eb4a826c2f9f37606f"
SRC_URI[sha256sum] = "4d5e5eee5f276424c32e9591f1b6c971baedc7b49f28ce03d1f48b1e5d6226a2"
