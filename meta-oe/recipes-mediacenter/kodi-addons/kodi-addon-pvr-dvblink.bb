SUMMARY = "Kodi Media Center PVR plugins"

PV = "${@bb.utils.contains("MACHINE_FEATURES", "kodi18", "4.7.3+git${SRCPV}", "3.4.8+git${SRCPV}", d)}"
PKGV = "${@bb.utils.contains("MACHINE_FEATURES", "kodi18", "4.7.3+git${GITPKGV}", "3.4.8+git${SRCPV}", d)}"

RDEPENDS_${PN} = "libtinyxml2"

KODIADDONPLUGIN = "dvblink"

require kodi-addon-pvr.inc
