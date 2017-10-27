DESCRIPTION = "QuadPiP plugin for VU+ UHD receivers"

require conf/license/openpli-gplv2.inc

S = "${WORKDIR}/git"
SRC_URI = "git://github.com/OpenPLi/enigma2-plugin-systemplugins-quadpip.git;protocol=http"

inherit allarch
inherit gitpkgv
PV = "git${SRCPV}"
PKGV = "git${GITPKGV}"
DST = "/usr/lib/enigma2/python/Plugins/SystemPlugins/QuadPiP"

FILES_${PN} = "${DST}/*"
PACKAGES = "${PN}"

do_install() {
	install -d "${D}/${DST}"
	cp -r "${S}"/* "${D}/${DST}"
	python -O -m compileall "${D}/${DST}"
}
