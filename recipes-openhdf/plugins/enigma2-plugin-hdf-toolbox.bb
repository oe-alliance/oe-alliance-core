DESCRIPTION = "HDF Toolbox"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "HDF-Team"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
VER ="1.0"
PR = "r0"

SRC_URI = "git://212.117.176.235:22225/opt/git/hdf-toolbox.git;protocol=ssh;user=git"

S = "${WORKDIR}/git"

FILES_${PN} = "/usr/* "

do_install() {
	cp -rp ${S}/usr ${D}/
}
