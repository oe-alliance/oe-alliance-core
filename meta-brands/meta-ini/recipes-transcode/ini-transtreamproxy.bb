SUMMARY = "streamproxy manages streaming data to a Mobile device using enigma2"
PRIORITY = "required"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r5"

PROVIDES += "virtual/transtreamproxy"
RPROVIDES_${PN} += "virtual/transtreamproxy"

SRC_URI = "git://code-ini.com/ini-transtreamproxy;protocol=git"

inherit autotools

S = "${WORKDIR}/git"

do_install() {
    install -d ${D}/usr/bin
    install -m 0755 ${WORKDIR}/build/src/transtreamproxy ${D}/usr/bin/transtreamproxy
}
