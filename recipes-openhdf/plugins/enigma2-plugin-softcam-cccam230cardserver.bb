DESCRIPTION = "CCcam 2.3.0"
MAINTAINER = "Cardserver"
SECTION = "base"
PRIORITY = "required"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
VER ="2.3.0"
PR = "r1"

SRC_URI[md5sum] = "1c196b5623fc7647bbd2f4797e99ca12"
SRC_URI[sha256sum] = "dbcbc2662e8386c2b3b74b5bcdf1956c6aecb82af9db9520f3cad5fe2e29e955"


SRC_URI = "http://addons.hdfreaks.cc/feeds/*git/enigma2-plugins-softcam-CCcam230_Cardserver.tar.gz"

S = "${WORKDIR}"

FILES_${PN} = "/usr/* \
		/etc/*"

do_install() {
cp -rp ${S}/usr ${S}/etc ${D}/
}
