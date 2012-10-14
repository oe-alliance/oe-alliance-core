DESCRIPTION = "Enigma2 Skin Nobile Mod"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "Nobile"

require conf/license/license-gplv2.inc

SRC_URI[md5sum] = "3ebfc56b70a41c4367c65bd72a45a7d9"
SRC_URI[sha256sum] = "01cded3b447b87035482eaa8a6205739c077bfb0316c227e9d2532a11344c8ee"

inherit gitpkgv
SRCREV = "${AUTOREV}"
VER ="1.0"
PR = "r9"

SRC_URI = "http://addons.hdfreaks.cc/feeds/*git/enigma2-plugins-skins-nobile_hdf_mod.tar.gz"

FILES_${PN} = "/usr/share/enigma2/ \
/etc/enigma2/settings"


S = "${WORKDIR}"

do_compile() {
}

do_install() {
install -d ${D}/usr/share
cp -rp ${S}/usr/share/* ${D}/usr/share/
chmod -R 777 ${D}/usr/share/enigma2/
}
