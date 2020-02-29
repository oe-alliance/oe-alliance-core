SUMMARY = "Kodi Media Center PVR plugins"

KODIADDONPLUGIN = "vuplus"

DEPENDS_append = "${@bb.utils.contains("MACHINE_FEATURES", "kodi18", "nlohmann-json", "", d)}"

require kodi-addon-pvr.inc