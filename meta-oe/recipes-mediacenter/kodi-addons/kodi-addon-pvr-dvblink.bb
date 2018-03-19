SUMMARY = "Kodi Media Center PVR plugins"

RDEPENDS_${PN} = "libtinyxml2"

KODIADDONPLUGIN = "dvblink"

SRCREV_pvr${KODIADDONPLUGIN} = "339c3ea77f8b1c052ad106419a8f857ac8f53e60"

require kodi-addon-pvr.inc