SUMMARY = "Kodi Media Center PVR plugins"

PV = "3.4.3+git${SRCPV}"
PKGV = "3.4.3+git${GITPKGV}"

KODIADDONPLUGIN = "njoy"

require kodi-addon-pvr.inc

SRC_URI_append = "${@bb.utils.contains("MACHINE_FEATURES", "kodi18", " file://0001-Fix-build-pvr-njoy.patch ", "", d)}"
