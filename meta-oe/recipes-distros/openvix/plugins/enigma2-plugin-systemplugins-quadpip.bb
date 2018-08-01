DESCRIPTION = "QuadPiP plugin for VU+ UHD receivers"

require conf/license/license-gplv2.inc

SRC_URI = "git://github.com/OpenPLi/enigma2-plugin-systemplugins-quadpip.git;protocol=http"

S = "${WORKDIR}/git"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "git${SRCPV}"
PKGV = "git${GITPKGV}"
DST = "/usr/lib/enigma2/python/Plugins/SystemPlugins/QuadPiP"
PR = "r4"

FILES_${PN} = "${DST}/*"
PACKAGES = "${PN}"

do_install_prepend() {
	rm -f ${S}/po/updateallpo.sh
}

do_install() {
	install -d "${D}/${DST}"
	cp -r "${S}"/* "${D}/${DST}"
	python -O -m compileall "${D}/${DST}"
}
