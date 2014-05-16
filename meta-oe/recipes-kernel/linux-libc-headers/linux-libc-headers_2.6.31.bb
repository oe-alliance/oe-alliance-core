COMPATIBLE_TARGET_SYS ?= "(?!nios2)"

require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

PR = "5"

SRC_URI += " \
        file://0001-implement-TIF_RESTORE_SIGMASK-support-and-enable-the.patch \
        file://powerpc-fix-build-with-make-3.82.patch \
        file://dvbapi-5.3.patch \
		file://ppp.patch  \
		file://types.patch \
		file://rfkill.patch \
"

SRC_URI[md5sum] = "84c077a37684e4cbfa67b18154390d8a"
SRC_URI[sha256sum] = "0acd83f7b85db7ee18c2b0b7505e1ba6fd722c36f49a8870a831c851660e3512"
