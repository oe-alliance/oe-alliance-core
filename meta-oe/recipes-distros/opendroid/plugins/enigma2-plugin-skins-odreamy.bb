SUMMARY = "Enigma2 Skin oDreamy"
MAINTAINER = "openDroid Team"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
inherit allarch

require conf/license/license-gplv2.inc

inherit gitpkgv allarch
SRCREV = "${AUTOREV}"
PV = "1.1+git${SRCPV}"
PKGV = "1.1+git${GITPKGV}"
VER="1.1"
PR = "r2"

SRC_URI="git://github.com/opendroid-Team/skins-oDreamy.git;protocol=https"
FILES_${PN} = "/usr/share ${libdir}"

S = "${WORKDIR}/git"
do_install() {
	install -d ${D}/usr/share
	cp -rp ${S}/usr/share/* ${D}/usr/share/
	chmod -R a+rX ${D}/usr/share/enigma2/
}
