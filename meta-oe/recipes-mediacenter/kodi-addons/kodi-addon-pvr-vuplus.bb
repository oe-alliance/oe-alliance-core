SUMMARY = "Kodi Media Center PVR plugins"

PKGV = "${PV}"

KODIADDONPLUGIN = "vuplus"

DEPENDS:append = "nlohmann-json"

require kodi-addon-pvr.inc
