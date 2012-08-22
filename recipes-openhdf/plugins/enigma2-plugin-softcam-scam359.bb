DESCRIPTION = "Scam 359"
MAINTAINER = "Scam Cardreader"
SECTION = "base"
PRIORITY = "required"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
VER ="3.59"
PR = "0"

SRC_URI[md5sum] = "5530abc1407b9b23b18100629c4e3109"
SRC_URI[sha256sum] = "9b62428bc3935434f59d8cbfbd128da5853c63730c3c07dc6763c89fea09ee71"


SRC_URI = "http://addons.hdfreaks.cc/feeds/*git/enigma2-plugins-softcam-SCam359_Cardserver.tar.gz"

S = "${WORKDIR}"

FILES_${PN} = "/usr/* \
		/etc/*"

do_install() {
cp -rp ${S}/usr ${S}/etc ${D}/
}
