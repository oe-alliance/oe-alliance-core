SUMMARY = "Kodi Media Center PVR plugins"

PKGV = "${PV}"

KODIADDONPLUGIN = "stalker"

DEPENDS:append = "nlohmann-json"

require kodi-addon-pvr.inc
