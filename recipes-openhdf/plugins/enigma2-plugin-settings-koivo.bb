DESCRIPTION = "Enigma2 Koivo settings"
MAINTAINER = "Settings"
SECTION = "base"
PRIORITY = "required"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
VER ="1.0"
PR = "r2"

SRC_URI[md5sum] = "ad8fe84ff9c8d1f8c5d89268756dd76a"
SRC_URI[sha256sum] = "69849ef829aa816aa9ae48eee64dbc144d14482960e15ec00da4e8b22f87c838"


SRC_URI = "http://addons.hdfreaks.cc/feeds/enigma2-plugins-update-Settings_Koivo_22_08_2012.tar.gz"

S = "${WORKDIR}"

FILES_${PN} = "/etc/* "

do_install() {
	cp -rp ${S}/etc ${D}/
}
