SUMMARY = "Kodi Media Center PVR plugins"

PKGV = "${PV}"

RDEPENDS_${PN} = "libtinyxml2"

KODIADDONPLUGIN = "dvblink"

require kodi-addon-pvr.inc
