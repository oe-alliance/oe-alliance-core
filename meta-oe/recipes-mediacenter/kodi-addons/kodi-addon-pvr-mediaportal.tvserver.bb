SUMMARY = "Kodi Media Center PVR plugins"

PV = "3.5.19+git${SRCPV}"
PKGV = "3.5.19+git${GITPKGV}"

KODIADDONPLUGIN = "mediaportal.tvserver"

require kodi-addon-pvr.inc

SRC_URI_append = " file://0001-drop-xlocale.h.patch"
