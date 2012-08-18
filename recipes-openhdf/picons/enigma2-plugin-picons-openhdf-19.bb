DESCRIPTION = "picons-openhdf-19"
MAINTAINER = "HDF Team"
PRIORITY = "required"
SECTION = "base"
LICENSE = "proprietary"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
VER ="1.0"
PR = "r1"


SRC_URI = "git://git@212.117.176.235:22225/opt/git/hdf-picons.git;protocol=ssh;user=git"

S = "${WORKDIR}/git"

FILES_${PN} = "/usr/* "

do_install() {
	echo "" "" "" "Kopiere Picons von ${S} nach ${D}"
	cp -rp ${S}/usr ${D}/
}
