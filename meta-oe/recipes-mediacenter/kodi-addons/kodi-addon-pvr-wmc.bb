SUMMARY = "Kodi Media Center PVR plugins"

PV = "2.4.5+git${SRCPV}"
PKGV = "2.4.5+git${GITPKGV}"

KODIADDONPLUGIN = "wmc"

require kodi-addon-pvr.inc

SRC_URI_append = " file://0001-Fix-build-pvr-wmc.patch"
