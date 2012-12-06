DESCRIPTION = "Enigma2 Skin Nobile Mod"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "Nobile"

require conf/license/license-gplv2.inc

SRC_URI[md5sum] = "1610f3a49d78c01b8529b5f0a63eacf5"
SRC_URI[sha256sum] = "4a14e34f15635801b7e968ade423d283242cca16b054ac0ccc02469483adf958"

inherit gitpkgv
SRCREV = "${AUTOREV}"
VER ="1.0"
PR = "r11"

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
