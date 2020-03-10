SUMMARY = "Kodi Media Center PVR plugins"

PV = "${@bb.utils.contains("MACHINE_FEATURES", "kodi18", "3.28.9+git${SRCPV}", "2.4.12+git${SRCPV}", d)}"
PKGV = "${@bb.utils.contains("MACHINE_FEATURES", "kodi18", "3.28.9+git${GITPKGV}", "2.4.12+git${SRCPV}", d)}"

KODIADDONPLUGIN = "vuplus"

DEPENDS_append = "${@bb.utils.contains("MACHINE_FEATURES", "kodi18", "nlohmann-json", "", d)}"

require kodi-addon-pvr.inc
