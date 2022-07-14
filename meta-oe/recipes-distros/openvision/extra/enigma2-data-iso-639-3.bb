SUMMARY = "iso-639-3.pck for enigma2"

require conf/license/license-gplv2.inc

inherit gitpkgv allarch
SRCREV = "${AUTOREV}"
PV = "git${SRCPV}"
PKGV = "git${GITPKGV}"

SRC_URI = "git://github.com/OpenVisionE2/openvision-xml.git;protocol=https;branch=master"

FILES:${PN} = "${datadir}"

S = "${WORKDIR}/git"

do_install() {
	install -d ${D}${datadir}/enigma2
	install -m 0644 ${S}/data/iso-639-3.pck ${D}${datadir}/enigma2/
}
