SUMMARY = "Kodi Media Center PVR plugins"

PV = "${@bb.utils.contains("MACHINE_FEATURES", "kodi18", "5.10.15+git${SRCPV}", "4.12.17+git${SRCPV}", d)}"
PKGV = "${@bb.utils.contains("MACHINE_FEATURES", "kodi18", "5.10.15+git${GITPKGV}", "4.12.17+git${SRCPV}", d)}"

KODIADDONPLUGIN = "mythtv"

require kodi-addon-pvr.inc
