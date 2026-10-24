SUMMARY = "Kodi Media Center PVR plugins"

PKGV = "${PV}"
PR = "r1"

KODIADDONPLUGIN = "iptvsimple"

DEPENDS:append = " pugixml"

require kodi-addon-pvr.inc
