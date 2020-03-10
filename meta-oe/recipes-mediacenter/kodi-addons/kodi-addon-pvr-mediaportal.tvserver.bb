SUMMARY = "Kodi Media Center PVR plugins"

PV = "${@bb.utils.contains("MACHINE_FEATURES", "kodi18", "3.5.19+git${SRCPV}", "2.4.21+git${SRCPV}", d)}"
PKGV = "${@bb.utils.contains("MACHINE_FEATURES", "kodi18", "3.5.19+git${GITPKGV}", "2.4.21+git${SRCPV}", d)}"

KODIADDONPLUGIN = "mediaportal.tvserver"

require kodi-addon-pvr.inc

SRC_URI_append = "${@bb.utils.contains("MACHINE_FEATURES", "kodi18", " file://0001-drop-xlocale.h-leia.patch ", " file://0001-drop-xlocale.h.patch ", d)}"
