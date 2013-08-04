MODULE = "WakeOnLan"
DESCRIPTION = "Send a WOL packet to devices"

require openplugins-distutils.inc

inherit gitpkgv
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r0"

require assume-gplv2.inc
