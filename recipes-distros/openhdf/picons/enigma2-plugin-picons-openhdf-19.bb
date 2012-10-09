DESCRIPTION = "picons-openhdf-19"
MAINTAINER = "HDF Team"
PRIORITY = "required"
SECTION = "base"
LICENSE = "proprietary"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
VER ="1.0"
PR = "r2"

SRC_URI[md5sum] = "8ee8bfd6802ee99fb38d03ca8d5ffd3c"
SRC_URI[sha256sum] = "f3ea6f6172dd63d6ec0aee1e2aa47800c994f84a1748a613237478e6765056df"

SRC_URI = "http://addons.hdfreaks.cc/feeds/Picons-GBUE/hdf-picons.tar.gz"

S = "${WORKDIR}/"

FILES_${PN} = "/usr/* "

do_install() {

	cp -rp ${S}/usr ${D}/
}
