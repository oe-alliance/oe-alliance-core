SUMMARY = "Kodi Media Center PVR plugins"

PV = "3.6.2+git${SRCPV}"
PKGV = "3.6.2+git${GITPKGV}"

KODIADDONPLUGIN = "demo"

require kodi-addon-pvr.inc

SRC_URI_append = " file://0001-Fix-build-pvr-demo.patch"
