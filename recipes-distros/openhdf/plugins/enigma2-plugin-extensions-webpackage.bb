DESCRIPTION = "Enigma2 Web Manager Package"
MAINTAINER = "SystemPlugins & Extensions"
SECTION = "base"
PRIORITY = "required"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
VER ="1.0"
PR = "r1"

SRC_URI[md5sum] = "0c25f53e3f259ba82b1916d95f17f57d"
SRC_URI[sha256sum] = "828ac66679a44e10320c169a070dc6a7d764891659d9bdb92bab63d5b4d60506"

RDEPENDS_${PN} = "python-difflib openssl python-pyopenssl"

SRC_URI = "http://addons.hdfreaks.cc/feeds/*git/enigma2-plugins-web_package_mips32el.tar.gz"

S = "${WORKDIR}"

FILES_${PN} = "/usr/*"


do_install() {
        cp -rp ${S}/usr ${D}/
}
