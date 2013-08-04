MODULE = "AutoShutDown"
DESCRIPTION = "automated power off for STB"
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit gitpkgv
SRCREV = ""
PV = "0.6+git${SRCPV}"
PKGV = "0.6+git${GITPKGV}"
PR = "r0"

require openplugins.inc

inherit autotools

FILES_${PN} = "/"

require assume-gplv2.inc
