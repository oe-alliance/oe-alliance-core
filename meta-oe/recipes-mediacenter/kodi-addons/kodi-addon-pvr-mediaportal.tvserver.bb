SUMMARY = "Kodi Media Center PVR plugins"

KODIADDONPLUGIN = "mediaportal.tvserver"

SRCREV_pvr${KODIADDONPLUGIN} = "dece80d3f144c3bfd0026e67d0e9f6f95eae6db8"

require kodi-addon-pvr.inc

SRC_URI_append = " file://0001-drop-xlocale.h.patch"