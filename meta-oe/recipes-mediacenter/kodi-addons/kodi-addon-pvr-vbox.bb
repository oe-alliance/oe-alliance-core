SUMMARY = "Kodi Media Center PVR plugins"

PV = "${@bb.utils.contains("MACHINE_FEATURES", "kodi18", "4.7.0+git${SRCPV}", "3.6.13+git${SRCPV}", d)}"
PKGV = "${@bb.utils.contains("MACHINE_FEATURES", "kodi18", "4.7.0+git${GITPKGV}", "3.6.13+git${SRCPV}", d)}"

KODIADDONPLUGIN = "vbox"

require kodi-addon-pvr.inc
