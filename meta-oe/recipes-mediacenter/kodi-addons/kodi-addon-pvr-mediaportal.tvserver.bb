SUMMARY = "Kodi Media Center PVR plugins"

PKGV = "${PV}"

KODIADDONPLUGIN = "mediaportal.tvserver"

require kodi-addon-pvr.inc

SRC_URI_append = "file://0001-drop-xlocale.h.patch"
