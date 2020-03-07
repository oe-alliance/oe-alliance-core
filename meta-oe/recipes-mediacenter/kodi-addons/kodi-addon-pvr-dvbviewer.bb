SUMMARY = "Kodi Media Center PVR plugins"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://src/client.cpp;md5=6420243dd3ea9f912a7099707dfd3e49"

PV = "3.7.13+git${SRCPV}"
PKGV = "3.7.13+git${GITPKGV}"

KODIADDONPLUGIN = "dvbviewer"

require kodi-addon-pvr.inc

SRC_URI_append = "${@bb.utils.contains("MACHINE_FEATURES", "kodi18", " file://0001-Fix-build-pvr-dvbviewer.patch ", "", d)}"

