SUMMARY = "Kodi Media Center PVR plugins"

PKGV = "${PV}"
PR = "r1"

KODIADDONPLUGIN = "stalker"

DEPENDS:append = " nlohmann-json"

require kodi-addon-pvr.inc
