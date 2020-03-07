SUMMARY = "Kodi Media Center PVR plugins"

PV = "4.7.3+git${SRCPV}"
PKGV = "4.7.3+git${GITPKGV}"

RDEPENDS_${PN} = "libtinyxml2"

KODIADDONPLUGIN = "dvblink"

require kodi-addon-pvr.inc
