SUMMARY = "Kodi Media Center PVR plugins"

PKGV = "${PV}"

KODIADDONPLUGIN = "mythtv"

require kodi-addon-pvr.inc

SRC_URI:append = " file://pvr-mythtv-fix-build-using-not-merged-upstream-commit.patch"
