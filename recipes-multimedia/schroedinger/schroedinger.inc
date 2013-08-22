HOMEPAGE = "http://schrodinger.sourceforge.net/"
LICENSE = "GPLv2+"
DEPENDS = "liboil orc-native orc alsa-lib"

INC_PR = "r1"

LIC_FILES_CHKSUM = "file://COPYING.GPL;md5=e181e3b7c66f5f96921d813c1074f833"

SRC_URI = "http://www.diracvideo.org/download/schroedinger/${P}.tar.gz;name=schroedingertargz"
SRC_URI[schroedingertargz.md5sum] = "d67ec48b7c506db8c8b49156bf409e60"
SRC_URI[schroedingertargz.sha256sum] = "345abcaa72ff0f2e9c1075e22f7141475ee4e6eea23a7f568b69ffc13cc1c723"

SRC_URI += " \
		file://configure.ac.patch \
		file://disable_testsuite.patch \
		file://add_types.patch \
	"

EXTRA_OECONF += "STAGING_DIR=${STAGING_DIR_NATIVE}"

inherit autotools

do_configure_append () {
        # Fix stupid libtool... handling. 
        # rpath handling can't be disabled and the Makefile's can't be regenerated..
        # (GNU sed required)
        sed -i s/^hardcode_libdir_flag_spec/#hardcode_libdir_flag_spec/ ${S}/*-libtool
}
