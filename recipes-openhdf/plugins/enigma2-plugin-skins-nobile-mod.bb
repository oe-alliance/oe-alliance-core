DESCRIPTION = "Enigma2 Skin Nobile Mod"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "Nobile"

require conf/license/license-gplv2.inc

SRC_URI[md5sum] = "d917ac51301b4910a365dd8080f2de5e"
SRC_URI[sha256sum] = "bdb2826342cca78e67b7291f5b58f3781d3c836b59af8554d854be3b1b0a903e"

inherit gitpkgv
SRCREV = "${AUTOREV}"
VER ="1.0"
PR = "r3"

SRC_URI = "http://addons.hdfreaks.cc/feeds/*git/enigma2-plugins-skins-nobile_hdf_mod.tar.gz"

FILES_${PN} = "/usr/share/enigma2/ \
/etc/enigma2/settings"


S = "${WORKDIR}"

do_compile() {
}

do_install() {
mkdir -p ${D}/etc/enigma2/
echo "config.skin.primary_skin=Nobile/skin.xml" >> ${D}/etc/enigma2/settings
install -d ${D}/usr/share
cp -rp ${S}/usr/share/* ${D}/usr/share/
chmod -R a+rX ${D}/usr/share/enigma2/
}