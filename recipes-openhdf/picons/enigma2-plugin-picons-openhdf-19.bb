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

SRC_URI[md5sum] = "5a641a00bc03dc1e0e7018277fd91b01"
SRC_URI[sha256sum] = "0c33c4853005cdbcd852f8deae7371b197fc6e41d34d26bdf0b0a5b939ad10e7"

SRC_URI = "http://addons.hdfreaks.cc/feeds/Picons-GBUE/hdf-picons.tar.gz"

S = "${WORKDIR}/"

FILES_${PN} = "/usr/* "

do_install() {

	cp -rp ${S}/usr ${D}/
}
