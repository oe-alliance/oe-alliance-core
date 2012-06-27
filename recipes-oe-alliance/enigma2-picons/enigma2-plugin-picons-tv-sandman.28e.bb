DESCRIPTION = "sandman 28e"
SECTION = "base"
PRIORITY = "required"
MAINTAINER = "silverfox0786"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

SRC_URI[md5sum] = "3dcc04f5e5cd7a5537aaa0881c4a4683"
SRC_URI[sha256sum] = "9566678662690cc2fb859775dd45fa316565e0b0e8e7ae247da0f7a912e17c21"

PR = "r1"
SRCDATE = "20120309"
PV = "${SRCDATE}"
SETTINGS_FILENAME = "sandman.28e.picons"

SRC_URI = "http://enigma2.world-of-satellite.com/picons/${SETTINGS_FILENAME}_${PV}.tar.gz"

S = "${WORKDIR}/picon"

inherit autotools pkgconfig

PACKAGES = "${PN}"

FILES_${PN} = "/picon"

do_install() {
	install -d ${D}/picon
	install -m 0644 ${WORKDIR}/picon/*.* ${D}/picon
}

do_install_append() {
	find ${D}/picon/ -name 'picon_default.png' -exec rm {} \;
}
