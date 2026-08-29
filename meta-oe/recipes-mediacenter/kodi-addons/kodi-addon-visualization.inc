SUMMARY = "Kodi ${KODIADDONNAME} music visualization"
HOMEPAGE = "https://github.com/xbmc/${KODIADDONNAME}"

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=435d4178fd08b25f9cf911f1c3a0ce1d"

inherit kodi-addon

DEPENDS += "glm virtual/libgles2"

# The concrete GLES package is added automatically from the linked SONAME.
# virtual/libgles2 is a build-time provider only and has no matching, portable
# runtime package name on every machine (notably the Edision GLES stacks).

KODIADDONBRANCH = "Piers"
KODIADDONNAME = "visualization.${KODIADDONPLUGIN}"

SRC_URI = "git://github.com/xbmc/${KODIADDONNAME}.git;protocol=https;branch=${KODIADDONBRANCH}"
PV = "22.1.0+gitr"

EXTRA_OECMAKE += "-DAPP_RENDER_SYSTEM=gles"
