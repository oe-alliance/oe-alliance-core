SUMMARY = "kodi inputstream addon for several manifest types"

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://src/main.cpp;beginline=1;endline=7;md5=45347fdc3c187ba13a5d93a1db696f47"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

inherit kodi-addon

DEPENDS += "expat pugixml"
RDEPENDS:${PN} += "ldd"
RRECOMMENDS:${PN} = "kernel-module-ext2"

#SRCREV = "c8f06ee08054d67a3dda3c8d1a6af38df1f5c688"
SRCREV = "d8e2a9bed7f2deb7ff373b7e9a0c7c8f6538a454"
PV = "22.3.4.r1+gitr"

KODIADDONBRANCH = "Piers"

SRC_URI = "git://github.com/xbmc/inputstream.adaptive;protocol=https;branch=${KODIADDONBRANCH} \
        file://define-INPUTSTREAM_MAX_STREAM_COUNT-ifndef.patch"

EXTRA_OECMAKE += "-DENABLE_INTERNAL_BENTO4=ON"

# Allow downloads during internals build
do_compile[network] = "1"

KODIADDONNAME = "inputstream.adaptive"

INSANE_SKIP:${PN} = "libdir dev-so"
