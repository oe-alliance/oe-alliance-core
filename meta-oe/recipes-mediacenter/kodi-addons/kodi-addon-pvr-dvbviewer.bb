SUMMARY = "Kodi Media Center PVR plugins"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "${@bb.utils.contains("MACHINE_FEATURES", "kodi18", "file://src/client.cpp;md5=6420243dd3ea9f912a7099707dfd3e49", "file://src/client.cpp;md5=c2e1e330bcf032b15729cdd1982d824a", d)}"

PV = "${@bb.utils.contains("MACHINE_FEATURES", "kodi18", "3.7.13+git${SRCPV}", "2.4.14+git${SRCPV}", d)}"
PKGV = "${@bb.utils.contains("MACHINE_FEATURES", "kodi18", "3.7.13+git${GITPKGV}", "2.4.14+git${SRCPV}", d)}"

KODIADDONPLUGIN = "dvbviewer"

require kodi-addon-pvr.inc

SRC_URI_append = "${@bb.utils.contains("MACHINE_FEATURES", "kodi18", " file://0001-Fix-build-pvr-dvbviewer.patch ", "", d)}"
