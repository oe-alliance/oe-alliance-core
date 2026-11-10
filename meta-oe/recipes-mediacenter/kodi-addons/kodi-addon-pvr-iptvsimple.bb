SUMMARY = "Kodi Media Center PVR plugins"

PKGV = "${PV}"
PR = "r2"

KODIADDONPLUGIN = "iptvsimple"

DEPENDS:append = " pugixml"

require kodi-addon-pvr.inc
