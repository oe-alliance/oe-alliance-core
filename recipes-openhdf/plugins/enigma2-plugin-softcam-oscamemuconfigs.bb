DESCRIPTION = "Oscam ymod"
MAINTAINER = "Configs"
SECTION = "base"
PRIORITY = "required"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
VER ="18"
PR = "t33"

SRC_URI[md5sum] = "9a116b2ffd13324219e1c561a07a8944"
SRC_URI[sha256sum] = "d314548ce02896714159e1bc5a276cb5c6dcec7a5faf64ef6323d94e9c085cb7"


SRC_URI = "http://addons.hdfreaks.cc/feeds/*git/enigma2-plugins-softcam-Configs_OscamEmu.tar.gz"

S = "${WORKDIR}"

FILES_${PN} = "/etc/* "

do_install() {
cp -rp ${S}/etc ${D}/
}