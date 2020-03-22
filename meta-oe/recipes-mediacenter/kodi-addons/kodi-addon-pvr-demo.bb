SUMMARY = "Kodi Media Center PVR plugins"

PV = "${@bb.utils.contains("MACHINE_FEATURES", "kodi18", "3.6.2+git${SRCPV}", "2.4.8+git${SRCPV}", d)}"
PKGV = "${@bb.utils.contains("MACHINE_FEATURES", "kodi18", "3.6.2+git${GITPKGV}", "2.4.8+git${SRCPV}", d)}"

KODIADDONPLUGIN = "demo"

require kodi-addon-pvr.inc

SRC_URI_append = "${@bb.utils.contains("MACHINE_FEATURES", "kodi18", " file://0001-Fix-build-pvr-demo.patch ", "", d)}"
