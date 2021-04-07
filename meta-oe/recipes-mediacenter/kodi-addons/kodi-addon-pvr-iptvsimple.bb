SUMMARY = "Kodi Media Center PVR plugins"

PKGV = "${PV}"

KODIADDONPLUGIN = "iptvsimple"

DEPENDS_append = "pugixml"

require kodi-addon-pvr.inc
