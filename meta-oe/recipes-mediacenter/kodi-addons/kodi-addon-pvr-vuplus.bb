SUMMARY = "Kodi Media Center PVR plugins"

PKGV = "${PV}"
PR = "r1"

KODIADDONPLUGIN = "vuplus"

DEPENDS:append = " nlohmann-json"

require kodi-addon-pvr.inc
