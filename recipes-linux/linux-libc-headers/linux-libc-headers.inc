DESCRIPTION = "Sanitized set of 2.6 kernel headers for the C library's use."
SECTION = "devel"
LICENSE = "GPLv2"
NATIVE_INSTALL_WORKS = "1"
BBCLASSEXTEND = "native"

RDEPENDS_${PN}-dev = ""
RRECOMMENDS_${PN}-dbg = "${PN}-dev (= ${EXTENDPV})"

inherit kernel-arch

COMPATIBLE_TARGET_SYS ?= "(?!nios2)"
