MODULE = "Satscan"
DESCRIPTION = "Blind scan on DVB-S"
RDEPENDS_${PN} = "virtual/blindscan-dvbs"
RDEPENDS_${PN} += "python-subprocess"
PACKAGE_ARCH = "${MACHINE_ARCH}"

SRCREV = ""

inherit gitpkgv

PV = "1.3+git${SRCPV}"
PKGV = "${PV}"
PR = "r0"

require openplugins-distutils.inc

require assume-gplv2.inc
