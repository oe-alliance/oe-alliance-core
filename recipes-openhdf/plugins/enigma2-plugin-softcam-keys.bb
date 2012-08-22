DESCRIPTION = "Softcam"
MAINTAINER = "keys"
SECTION = "base"
PRIORITY = "required"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
VER ="1"
PR = "0"

SRC_URI[md5sum] = "2f6b6a041294e9c216c0ea365ecb2672"
SRC_URI[sha256sum] = "45da4e82a62b79740f553ff0dee29a0ca35f65be78b015b17eca2be7a80ecc5d"


SRC_URI = "http://addons.hdfreaks.cc/feeds/*git/enigma2-plugins-softcam-SoftCamKeys.tar.gz"

S = "${WORKDIR}"

FILES_${PN} = "/usr/* \
		/etc/*"

do_install() {
cp -rp ${S}/usr ${S}/etc ${D}/
}
