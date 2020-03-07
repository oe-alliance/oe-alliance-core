SUMMARY = "Kodi Media Center PVR plugins"

PV = "${@bb.utils.contains("MACHINE_FEATURES", "kodi18", "3.6.3+git${SRCPV}", "2.6.35+git${SRCPV}", d)}"
PKGV = "${@bb.utils.contains("MACHINE_FEATURES", "kodi18", "3.6.3+git${GITPKGV}", "2.6.35+git${SRCPV}", d)}"

KODIADDONPLUGIN = "vdr.vnsi"

require kodi-addon-pvr.inc
