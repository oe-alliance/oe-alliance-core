SUMMARY = "Kodi Media Center PVR plugins"

PKGV = "${PV}"

KODIADDONPLUGIN = "iptvsimple"

DEPENDS_append = "${@bb.utils.contains("MACHINE_FEATURES", "kodi18", "rapidxml", "", d)}"

require kodi-addon-pvr.inc
