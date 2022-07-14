SUMMARY = "RC files and STB pictures for Open WebIF"

require conf/license/license-gplv2.inc

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "git${SRCPV}"
PKGV = "git${GITPKGV}"

SRC_URI = "git://github.com/OpenVisionE2/ov-remotes.git;protocol=https;branch=master"

FILES:${PN} = "${datadir}"

S = "${WORKDIR}/git"
SHTML = "${WORKDIR}/git/html"

do_install() {
	install -d ${D}${datadir}/enigma2/rc
	if [ -e ${SHTML}/${RCNAME}.html ]; then
		install -m 0644 ${SHTML}/${RCNAME}.html ${D}${datadir}/enigma2/rc/
	fi
	install -m 0644 ${SHTML}/dmm1.html ${D}${datadir}/enigma2/rc/
}
