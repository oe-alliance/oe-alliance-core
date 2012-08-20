DESCRIPTION = "HDF Toolbox"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "HDF-Team"

require conf/license/license-gplv2.inc

inherit autotools
SRCREV = "${AUTOREV}"
VER ="1.0"
PR = "r1"

SRC_URI[md5sum] = "2172a08d465c333e9c2eee5419efc0b1"
SRC_URI[sha256sum] = "117b4d6b5eb5065ce997746379c4156deec16ea6decbb30bf62369198ee3d591"

SRC_URI = "http://addons.hdfreaks.cc/HDF-Toolbox/release.tgz"

S = "${WORKDIR}/"

FILES_${PN} = "/usr/* "

do_install() {
	cp -rp ${S}/usr ${D}/
}
