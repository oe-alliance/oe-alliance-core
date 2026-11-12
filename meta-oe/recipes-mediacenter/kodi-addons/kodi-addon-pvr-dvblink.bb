SUMMARY = "Kodi Media Center PVR plugins"

PKGV = "${PV}"
PR = "r1"

RDEPENDS:${PN} = "libtinyxml2"

KODIADDONPLUGIN = "dvblink"

require kodi-addon-pvr.inc
