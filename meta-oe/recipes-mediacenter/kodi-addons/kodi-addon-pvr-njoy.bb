SUMMARY = "Kodi Media Center PVR plugins"

PV = "${@bb.utils.contains("MACHINE_FEATURES", "kodi18", "3.4.3+git${SRCPV}", "2.4.5+git${SRCPV}", d)}"
PKGV = "${@bb.utils.contains("MACHINE_FEATURES", "kodi18", "3.4.3+git${GITPKGV}", "2.4.5+git${SRCPV}", d)}"

KODIADDONPLUGIN = "njoy"

require kodi-addon-pvr.inc

SRC_URI_append = "${@bb.utils.contains("MACHINE_FEATURES", "kodi18", " file://0001-Fix-build-pvr-njoy.patch ", "", d)}"
