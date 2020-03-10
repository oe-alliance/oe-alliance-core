SUMMARY = "Kodi Media Center PVR plugins"

PV = "${@bb.utils.contains("MACHINE_FEATURES", "kodi18", "3.9.8+git${SRCPV}", "2.4.14+git${SRCPV}", d)}"
PKGV = "${@bb.utils.contains("MACHINE_FEATURES", "kodi18", "3.9.8+git${GITPKGV}", "2.4.14+git${SRCPV}", d)}"

KODIADDONPLUGIN = "iptvsimple"

DEPENDS_append = "${@bb.utils.contains("MACHINE_FEATURES", "kodi18", "rapidxml", "", d)}"

require kodi-addon-pvr.inc
