COMPATIBLE_TARGET_SYS ?= "(?!nios2)"

require linux-libc-headers.inc

PRINC = "1"

SRC_URI += " \
		file://0001-implement-TIF_RESTORE_SIGMASK-support-and-enable-the.patch \
		file://powerpc-fix-build-with-make-3.82.patch \
		file://dvbapi-5.3.patch \
"
