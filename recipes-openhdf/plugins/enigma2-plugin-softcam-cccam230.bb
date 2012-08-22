DESCRIPTION = "CCcam 2.3.0"
MAINTAINER = "SoftCam"
SECTION = "base"
PRIORITY = "required"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
VER ="2.3.0"
PR = "r1"

SRC_URI[md5sum] = "36dd981eb9b4e7eef0d67f323c362f16"
SRC_URI[sha256sum] = "6d035d7785e99d87a5cb8e8f2abe5d47a8f0298cab64b44e195870eb7fa9b103"


SRC_URI = "http://addons.hdfreaks.cc/feeds/*git/enigma2-plugins-softcam-CCcam230.tar.gz"

S = "${WORKDIR}"

FILES_${PN} = "/usr/* \
		/etc/*"

do_install() {
cp -rp ${S}/usr ${S}/etc ${D}/
}
