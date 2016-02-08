require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

PR = "6"

SRC_URI += " \
        file://001-fix_kernel_build_xargs.patch \
"

SRC_URI[md5sum] = "2220321a0a14d86200322e51dd5033e2"
SRC_URI[sha256sum] = "97e48f31ed2197f4e7e4938d4fab8da522cf80e60c6ce69668b0805904499305"
