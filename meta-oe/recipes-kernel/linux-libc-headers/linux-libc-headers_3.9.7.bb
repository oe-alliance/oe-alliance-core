require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

PR = "2"

SRC_URI += " \
        file://001-fix_kernel_build_xargs.patch \
"

SRC_URI[md5sum] = "2de8f625249279a071005b57250e9b20"
SRC_URI[sha256sum] = "f12aa72e3d8314f15f00d149617e3464adabb71e389f31963a0ae75d0a96f485"
