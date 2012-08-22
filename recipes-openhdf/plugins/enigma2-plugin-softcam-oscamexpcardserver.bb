DESCRIPTION = "Oscam Experimental"
MAINTAINER = "Oscam Cardserver"
SECTION = "base"
PRIORITY = "required"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
VER ="1.20"
PR = "7246"

SRC_URI[md5sum] = "6406e6b0330bf1f9cd9aaf2ec884cce3"
SRC_URI[sha256sum] = "427d62b29f24bd6fb73f172fde9275eb13fc81ba2859f0de59316cd6d77b4a64"


SRC_URI = "http://addons.hdfreaks.cc/feeds/*git/enigma2-plugins-softcam-OscamExperimental_Cardserver.tar.gz"

S = "${WORKDIR}"

FILES_${PN} = "/usr/* \
		/etc/*"

do_install() {
cp -rp ${S}/usr ${S}/etc ${D}/
}
