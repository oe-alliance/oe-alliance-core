SUMMARY = "Kodi Media Center PVR plugins"

PV = "${@bb.utils.contains("MACHINE_FEATURES", "kodi18", "2.4.6+git${SRCPV}", "1.4.8+git${SRCPV}", d)}"
PKGV = "${@bb.utils.contains("MACHINE_FEATURES", "kodi18", "2.4.6+git${GITPKGV}", "1.4.8+git${SRCPV}", d)}"

KODIADDONPLUGIN = "pctv"

require kodi-addon-pvr.inc
