require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

SRC_URI = "${KERNELORG_MIRROR}/linux/kernel/v3.x/linux-${PV}.tar.xz"

SRC_URI_append_vuultimo4k += " file://linux_dmx_source_dvr.patch"

SRC_URI[md5sum] = "d3fc8316d4d4d04b65cbc2d70799e763"
SRC_URI[sha256sum] = "be42511fe5321012bb4a2009167ce56a9e5fe362b4af43e8c371b3666859806c"
