SUMMARY = "Kodi Media Center PVR plugins"

PKGV = "${PV}"

KODIADDONPLUGIN = "iptvsimple"

DEPENDS:append = "pugixml"

require kodi-addon-pvr.inc
