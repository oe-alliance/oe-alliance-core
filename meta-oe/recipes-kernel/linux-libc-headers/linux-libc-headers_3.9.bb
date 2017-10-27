require recipes-kernel/linux-libc-headers/linux-libc-headers.inc


SRC_URI += " \
        file://001-fix_kernel_build_xargs.patch \
"

SRC_URI[md5sum] = "4348c9b6b2eb3144d601e87c19d5d909"
SRC_URI[sha256sum] = "60bc3e64ee5dc778de2cd7cd7640abf518a4c9d4f31b8ed624e16fad53f54541"
