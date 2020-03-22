SUMMARY = "Kodi Media Center PVR plugins"

PV = "${@bb.utils.contains("MACHINE_FEATURES", "kodi18", "3.4.10+git${SRCPV}", "2.8.8+git${SRCPV}", d)}"
PKGV = "${@bb.utils.contains("MACHINE_FEATURES", "kodi18", "3.4.10+git${GITPKGV}", "2.8.8+git${SRCPV}", d)}"

KODIADDONPLUGIN = "stalker"

require kodi-addon-pvr.inc
