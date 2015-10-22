require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

PR = "0"

SRC_URI = "${KERNELORG_MIRROR}/linux/kernel/v4.x/linux-${PV}.tar.xz"

SRC_URI[md5sum] = "2a538d1dac3f5426c7a8ab8ef20e95c2"
SRC_URI[sha256sum] = "7fdf35bf364cc88a9797dac0bc6c0f9951ae031c1d937f61583a649887bb8dac"
