SUMMARY = "Kodi Media Center PVR plugins"

PV = "${@bb.utils.contains("MACHINE_FEATURES", "kodi18", "3.3.16+git${SRCPV}", "2.4.15+git${SRCPV}", d)}"
PKGV = "${@bb.utils.contains("MACHINE_FEATURES", "kodi18", "3.3.16+git${GITPKGV}", "2.4.15+git${SRCPV}", d)}"

KODIADDONPLUGIN = "nextpvr"

require kodi-addon-pvr.inc
