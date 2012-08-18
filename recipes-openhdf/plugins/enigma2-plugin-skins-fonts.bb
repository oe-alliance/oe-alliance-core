DESCRIPTION = "Enigma2 Fonts"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "HDF"

require conf/license/license-gplv2.inc

SRC_URI[md5sum] = "ff3e769ea4844094b08a74c497ad8299"
SRC_URI[sha256sum] = "a0f2326c6241ff29b91bac9333603e839687a7a7b95256e397de83f8376e5d0f"

inherit gitpkgv
SRCREV = "${AUTOREV}"


VER ="1.0"
PR = "r0"

SRC_URI = "http://addons.hdfreaks.cc/feeds/enigma2-plugins-skins-All_the_needed_Fonts.tar.gz"

FILES_${PN} = "/usr/share/fonts/"


S = "${WORKDIR}"

do_compile() {
}

do_install() {
	install -d ${D}/usr/share
	cp -rp ${S}/usr/share/* ${D}/usr/share/
	chmod -R a+rX ${D}/usr/share/fonts/
}
