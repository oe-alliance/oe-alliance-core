SUMMARY = "Utilities for transponder & dvb-c blindscan"
SECTION = "base"
PRIORITY = "optional"
RDEPENDS_${PN} = "ncurses"

PACKAGE_ARCH = "${MACHINE_ARCH}"

LICENSE = "CLOSED"
require conf/license/license-close.inc

PACKAGES = "octagon-blindscan-dvbc-utils octagon-blindscan-dvbc-utils-dbg"

PROVIDES += "virtual/blindscan-dvbc"
RPROVIDES_octagon-blindscan-dvbc-utils += "virtual/blindscan-dvbc"

SRC_URI = "http://source.mynonpublic.com/octagon/octagon-dvbc-blindscan-1.1.zip"

PV = "1.1"
PR = "r0"

S = "${WORKDIR}/"

FILES_octagon-blindscan-dvbc-utils = "${bindir}/tda1002x"
FILES_octagon-blindscan-dvbc-utils-dbg = "${bindir}/.debug/tda1002x"


do_install() {
    install -d ${D}/${bindir}/
    install -m 0755 "${S}/tda1002x" "${D}/${bindir}"
}

SRC_URI[md5sum] = "23cb152a57a99b28f1bb74d95f4c3109"
SRC_URI[sha256sum] = "d9aac4908e691e4be732a1325bfd3a542c41e8cc83d6447d260668234b240c78"