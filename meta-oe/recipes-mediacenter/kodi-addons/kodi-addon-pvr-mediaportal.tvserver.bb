SUMMARY = "Kodi Media Center PVR plugins"

PKGV = "${PV}"

KODIADDONPLUGIN = "mediaportal.tvserver"

require kodi-addon-pvr.inc

SRC_URI_append = "${@bb.utils.contains("MACHINE_FEATURES", "kodi18", " file://0001-drop-xlocale.h-leia.patch ", " file://0001-drop-xlocale.h.patch ", d)}"
