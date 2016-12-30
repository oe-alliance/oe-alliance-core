SUMMARY = "Utilities for transponder & dvb-c blindscan"
SECTION = "base"
PRIORITY = "optional"
RDEPENDS_${PN} = "ncurses"

PACKAGE_ARCH = "${MACHINE_ARCH}"

LICENSE = "CLOSED"
require conf/license/license-close.inc

PACKAGES = "broadmedia-blindscan-dvbc-utils broadmedia-blindscan-dvbc-utils-dbg"

PROVIDES += "virtual/blindscan-dvbc"
RPROVIDES_broadmedia-blindscan-dvbc-utils += "virtual/blindscan-dvbc"

SRC_URI = "http://source.mynonpublic.com/broadmedia/broadmedia-dvbc-blindscan-1.0.zip"

PV = "1.0"
PR = "r1"

S = "${WORKDIR}/"

FILES_broadmedia-blindscan-dvbc-utils = "${bindir}/tda1002x"
FILES_broadmedia-blindscan-dvbc-utils-dbg = "${bindir}/.debug/tda1002x"


do_install() {
    install -d ${D}/${bindir}/
    install -m 0755 "${S}/tda1002x" "${D}/${bindir}"
}

SRC_URI[md5sum] = "1e27530c471dc99d9e0bf61582fe2a16"
SRC_URI[sha256sum] = "1150ca131abb529faa55c6050c06519fde9efb1aefa805a6630abceb123726db"