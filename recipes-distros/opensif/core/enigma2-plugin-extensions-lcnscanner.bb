DESCRIPTION = "LCN scanner for DVB-T services"
DEPENDS = "enigma2 python"

require conf/license/license-gplv2.inc

inherit gitpkgv

PV = "1.0-git${SRCPV}"
PKGV = "1.0-git${GITPKGV}"
PR = "r1"
SRCREV = "${AUTOREV}" 

SRC_URI="git://github.com/SIFTeam/lcnscanner.git;protocol=git"
S = "${WORKDIR}/git"

FILES_${PN} = "/usr/*"

do_install() {
	oe_runmake 'D=${D}' install
}
