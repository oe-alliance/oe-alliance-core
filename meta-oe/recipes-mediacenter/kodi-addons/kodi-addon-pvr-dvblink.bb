SUMMARY = "Kodi Media Center PVR plugins"

RDEPENDS_${PN} = "libtinyxml2"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://src/client.cpp;md5=b706d115f572ad4392eafa1289056075"

KODIADDONPLUGIN = "dvblink"

SRCREV_pvr${KODIADDONPLUGIN} = "7296c5b50d6a24131260db95a2b98d417833203f"

require kodi-addon-pvr.inc