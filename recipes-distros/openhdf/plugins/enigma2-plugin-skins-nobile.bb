DESCRIPTION = "Enigma2 Skin Nobile Mod"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "Nobile"

require conf/license/license-gplv2.inc

SRC_URI[md5sum] = "850c31c69f9dff5237e964f64f3b1809"
SRC_URI[sha256sum] = "f55ef11ec1003aa1b549536baaf4851f41395a5fddd9cc4d92b6e7fe8a0b7ba9"

inherit gitpkgv
SRCREV = "${AUTOREV}"
VER ="1.0"
PR = "r15"

SRC_URI = "http://addons.hdfreaks.cc/feeds/*git/enigma2-plugins-skins-nobile.tar.gz"

FILES_${PN} = "/usr/share/enigma2/"


S = "${WORKDIR}"

do_compile() {
}

do_install() {
install -d ${D}/usr/share
cp -rp ${S}/usr/share/* ${D}/usr/share/
chmod -R 777 ${D}/usr/share/enigma2/
}
