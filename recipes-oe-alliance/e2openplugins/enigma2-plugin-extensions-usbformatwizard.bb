MODULE = "UsbFormatWizard"
DESCRIPTION = "Wizard to Format Usb devices "

SRCREV = ""

inherit gitpkgv
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r1"

require openplugins-distutils.inc

require assume-gplv2.inc
