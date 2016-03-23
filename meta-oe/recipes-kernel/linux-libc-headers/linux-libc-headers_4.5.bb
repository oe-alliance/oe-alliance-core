require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

SRC_URI = "${KERNELORG_MIRROR}/linux/kernel/v4.x/linux-${PV}.tar.xz \
    file://fsxattr.patch \
"

SRC_URI[md5sum] = "a60d48eee08ec0536d5efb17ca819aef"
SRC_URI[sha256sum] = "a40defb401e01b37d6b8c8ad5c1bbab665be6ac6310cdeed59950c96b31a519c"
