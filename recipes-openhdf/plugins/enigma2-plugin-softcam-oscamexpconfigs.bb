DESCRIPTION = "Oscam Experimental"
MAINTAINER = "Configs"
SECTION = "base"
PRIORITY = "required"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
VER ="1.20"
PR = "7246"

SRC_URI[md5sum] = "dc44a557be2f94acf9292e015c6dd169"
SRC_URI[sha256sum] = "1543b0365dfa6d2eaae1050276986609f299408956439156d83daad996bc7f03"


SRC_URI = "http://addons.hdfreaks.cc/feeds/*git/enigma2-plugins-softcam-Configs_OscamExperimental.tar.gz"

S = "${WORKDIR}"

FILES_${PN} = "/etc/* "

do_install() {
cp -rp ${S}/etc ${D}/
}