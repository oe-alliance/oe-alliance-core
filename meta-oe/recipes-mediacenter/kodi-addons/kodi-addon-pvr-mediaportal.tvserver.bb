SUMMARY = "Kodi Media Center PVR plugins"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://src/client.cpp;md5=053feee3738f244130e4958db85d8c90"

KODIADDONPLUGIN = "mediaportal.tvserver"

SRCREV_pvr${KODIADDONPLUGIN} = "d4dad613215f56da490f9086ba30a761b9967b0c"

require kodi-addon-pvr.inc

SRC_URI_append = " file://0001-drop-xlocale.h.patch"