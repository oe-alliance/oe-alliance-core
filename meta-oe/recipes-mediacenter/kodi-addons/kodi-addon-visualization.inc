SUMMARY = "Kodi ${KODIADDONNAME} music visualization"
HOMEPAGE = "https://github.com/xbmc/${KODIADDONNAME}"

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=435d4178fd08b25f9cf911f1c3a0ce1d"

inherit kodi-addon

DEPENDS += "glm virtual/libgles2"

# Most GLES stacks add their concrete runtime package automatically from the
# linked SONAME. Vendor stacks excluded from shlib scanning can provide it via
# this machine-level hook. Do not use virtual/libgles2 as an RDEPENDS package.
KODI_VISUALIZATION_GLES_RDEPENDS ??= ""
RDEPENDS:${PN} += "${KODI_VISUALIZATION_GLES_RDEPENDS}"

KODIADDONBRANCH = "Piers"
KODIADDONNAME = "visualization.${KODIADDONPLUGIN}"

SRC_URI = "git://github.com/xbmc/${KODIADDONNAME}.git;protocol=https;branch=${KODIADDONBRANCH}"
PV = "22.1.0+gitr"

EXTRA_OECMAKE += "-DAPP_RENDER_SYSTEM=gles"
