DESCRIPTION = "Oscam Experimental"
MAINTAINER = "Oscam"
SECTION = "base"
PRIORITY = "required"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
VER ="1.20"
PR = "7246"

SRC_URI[md5sum] = "c401903eec99195fe38a4722f4fd6bf7"
SRC_URI[sha256sum] = "19fc1028923b982c8e3c2607434099024230ab43cffdb0279a9fef0562d40e05"


SRC_URI = "http://addons.hdfreaks.cc/feeds/*git/enigma2-plugins-softcam-OscamExperimental.tar.gz"

S = "${WORKDIR}"

FILES_${PN} = "/usr/* \
		/etc/*"

do_install() {
cp -rp ${S}/usr ${S}/etc ${D}/
}
