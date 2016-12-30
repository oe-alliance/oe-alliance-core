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

SRC_URI = "http://source.mynonpublic.com/octagon/octagon-dvbc-blindscan-1.0.zip"

PV = "1.0"
PR = "r0"

S = "${WORKDIR}/"

FILES_octagon-blindscan-dvbc-utils = "${bindir}/tda1002x"
FILES_octagon-blindscan-dvbc-utils-dbg = "${bindir}/.debug/tda1002x"


do_install() {
    install -d ${D}/${bindir}/
    install -m 0755 "${S}/tda1002x" "${D}/${bindir}"
}

SRC_URI[md5sum] = "694b40776258ac1c24bb89f097dec4f5"
SRC_URI[sha256sum] = "a97ef250ba3892ff762f567f2d06a7272169c67b73f197a36445ad5a47b8a2f4"