SUMMARY = "Kodi Media Center PVR plugins"

PV = "${@bb.utils.contains("MACHINE_FEATURES", "kodi18", "3.5.5+git${SRCPV}", "2.5.9+git${SRCPV}", d)}"
PKGV = "${@bb.utils.contains("MACHINE_FEATURES", "kodi18", "3.5.5+git${GITPKGV}", "2.5.9+git${SRCPV}", d)}"

KODIADDONPLUGIN = "argustv"

require kodi-addon-pvr.inc
