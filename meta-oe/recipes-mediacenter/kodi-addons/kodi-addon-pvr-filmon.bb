SUMMARY = "Kodi Media Center PVR plugins"

PV = "${@bb.utils.contains("MACHINE_FEATURES", "kodi18", "2.4.5+git${SRCPV}", "1.4.10+git${SRCPV}", d)}"
PKGV = "${@bb.utils.contains("MACHINE_FEATURES", "kodi18", "2.4.5+git${GITPKGV}", "1.4.10+git${SRCPV}", d)}"

KODIADDONPLUGIN = "filmon"

require kodi-addon-pvr.inc
