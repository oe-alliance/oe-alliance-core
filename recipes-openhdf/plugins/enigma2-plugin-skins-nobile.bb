DESCRIPTION = "Enigma2 Skin Noble"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "Noble"

require conf/license/license-gplv2.inc

SRC_URI[md5sum] = "562b4edf4ea2af44eab9dd7700d2025e"
SRC_URI[sha256sum] = "7dc1fcd66c1546c8a2ac8eadc01e80b67f2221c8a3c9f7ffb95088cb6c01b939"

inherit gitpkgv
SRCREV = "${AUTOREV}"
VER ="1.0"
PR = "r3"

SRC_URI = "http://addons.hdfreaks.cc/feeds/enigma2-plugins-skins-Nobile_mod_with_Second_Infobar.tar.gz"

FILES_${PN} = "/usr/share/enigma2/"


S = "${WORKDIR}"

do_compile() {
}

do_install() {
	install -d ${D}/usr/share
	cp -rp ${S}/usr/share/* ${D}/usr/share/
	chmod -R a+rX ${D}/usr/share/enigma2/
}
