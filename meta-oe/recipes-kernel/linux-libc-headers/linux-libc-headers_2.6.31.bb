COMPATIBLE_TARGET_SYS ?= "(?!nios2)"

require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

SRC_URI += " \
        file://0001-implement-TIF_RESTORE_SIGMASK-support-and-enable-the.patch \
        file://powerpc-fix-build-with-make-3.82.patch \
        file://dvbapi-5.3.patch \
        file://ppp.patch  \
        file://types.patch \
        file://rfkill.patch \
"

SRC_URI[md5sum] = "c6a901042a2dc9552bc6f475887a5ef3"
SRC_URI[sha256sum] = "4ad0df33ca20f170fa0a05ad2bcf8fb18bf457aba7f909434ed13461c59be80a"
