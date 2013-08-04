MODULE = "BhTunerServer"
DESCRIPTION = "Build a virtual channel list of .m3u files to allow streaming from a client vial lan or UPnP "

SRCREV = ""

inherit gitpkgv
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r1"

require openplugins-distutils.inc

require assume-gplv2.inc
