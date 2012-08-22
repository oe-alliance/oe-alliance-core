DESCRIPTION = "Oscam ymod"
MAINTAINER = "ymod cardserver"
SECTION = "base"
PRIORITY = "required"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
VER ="18"
PR = "t33"

SRC_URI[md5sum] = "c1c6654bddd5c795d5fc64940481839c"
SRC_URI[sha256sum] = "fc22aee19208d692060800fd9b96cb18efeec7a82dc16cd2055273e0397e400d"


SRC_URI = "http://addons.hdfreaks.cc/feeds/*git/enigma2-plugins-softcam-OscamEmu_Cardserver.tar.gz"

S = "${WORKDIR}"

FILES_${PN} = "/usr/* \
		/etc/*"

do_install() {
cp -rp ${S}/usr ${S}/etc ${D}/
}
