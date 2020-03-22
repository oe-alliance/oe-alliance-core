SUMMARY = "Kodi Media Center PVR plugins"

PV = "${@bb.utils.contains("MACHINE_FEATURES", "kodi18", "4.4.19+git${SRCPV}", "3.4.28+git${SRCPV}", d)}"
PKGV = "${@bb.utils.contains("MACHINE_FEATURES", "kodi18", "4.4.19+git${GITPKGV}", "3.4.28+git${SRCPV}", d)}"

KODIADDONPLUGIN = "hts"

require kodi-addon-pvr.inc
