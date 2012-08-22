DESCRIPTION = "Scam 360"
MAINTAINER = "Scam Cardreader"
SECTION = "base"
PRIORITY = "required"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
VER ="3.60"
PR = "0"

SRC_URI[md5sum] = "bb62a263332f56802385ccfb753bdbad"
SRC_URI[sha256sum] = "dfef28dd8a5febe25edc91f3444e1c13470458bb3f3edb7cf98d970afea97f48"


SRC_URI = "http://addons.hdfreaks.cc/feeds/*git/enigma2-plugins-softcam-SCam360_Cardserver.tar.gz"

S = "${WORKDIR}"

FILES_${PN} = "/usr/* \
		/etc/*"

do_install() {
cp -rp ${S}/usr ${S}/etc ${D}/
}
