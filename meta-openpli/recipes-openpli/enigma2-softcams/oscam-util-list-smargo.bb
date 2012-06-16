DESCRIPTION = "List smargo utility"
LICENSE = "GPLv3"
PV = "svn${SRCPV}"
PR = "r0"

DEPENDS = "libusb"

INHIBIT_PACKAGE_STRIP = "1"

SRC_URI = " \
		svn://oscam.to/svn/oscam/trunk;module=utils;proto=http;scmdata=keep \
		file://CMakeLists.txt.diff \
		"

LIC_FILES_CHKSUM = "file://list_smargo.c;startline=5;endline=17;md5=d0df56ed6dc45b68c4946b217f2aeb84"

S = "${WORKDIR}/utils"

OECMAKE_SOURCEPATH = "${S}"
EXTRA_OEMAKE = "-c ${OECMAKE_SOURCEPATH} "

do_generate_toolchain_file_append() {
	echo "set( LIBUSBDIR ${STAGING_DIR_HOST} ) " >> ${WORKDIR}/toolchain.cmake
}

EXTRA_OECMAKE += "\
		-DOSCAM_SYSTEM_NAME=Tuxbox \
		"

inherit cmake

do_install() {
	install -d ${D}/usr/bin
	install -m 0755 ${S}/list_smargo ${D}/usr/bin/list_smargo
}
