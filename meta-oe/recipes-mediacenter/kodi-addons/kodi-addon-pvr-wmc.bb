SUMMARY = "Kodi Media Center PVR plugins"

PKGV = "${PV}"

KODIADDONPLUGIN = "wmc"

require kodi-addon-pvr.inc

SRC_URI_append = "${@bb.utils.contains("MACHINE_FEATURES", "kodi18", " file://0001-Fix-build-pvr-wmc.patch ", "", d)}"
