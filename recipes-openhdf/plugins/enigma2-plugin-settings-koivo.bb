DESCRIPTION = "Enigma2 Koivo settings"
MAINTAINER = "Settings"
SECTION = "base"
PRIORITY = "required"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
VER ="1.0"
PR = "r1"

SRC_URI[md5sum] = "481beba7d47d0d1a9328659b51f4d555"
SRC_URI[sha256sum] = "72f24459697a7acf605e292db7c79bdb5c86d6d2637a0293b0d9a6ea8ae5dce8"


SRC_URI = "http://addons.hdfreaks.cc/feeds/enigma2-plugins-update-Settings_Koivo_30_04_2012.tar.gz"

S = "${WORKDIR}"

FILES_${PN} = "/etc/* "

do_install() {
	cp -rp ${S}/etc ${D}/
}
