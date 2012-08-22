DESCRIPTION = "CCcam 2.3.0"
MAINTAINER = "Configs"
SECTION = "base"
PRIORITY = "required"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
VER ="2.3.0"
PR = "r1"

SRC_URI[md5sum] = "d538413c8c74c92bfdb52cd68b96400a"
SRC_URI[sha256sum] = "8786d35f59e9a9445d05029702f56458da4e2250bb21cbed442c57db17299aaf"


SRC_URI = "http://addons.hdfreaks.cc/feeds/*git/enigma2-plugins-softcam-Configs_CCcam.tar.gz"

S = "${WORKDIR}"

FILES_${PN} = "/etc/* "

do_install() {
cp -rp ${S}/etc ${D}/
}
