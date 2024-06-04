SUMMARY = "kodi inputstream addon for several manifest types"

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://src/main.cpp;beginline=1;endline=7;md5=45347fdc3c187ba13a5d93a1db696f47"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

inherit kodi-addon

DEPENDS += "expat pugixml"
RDEPENDS:${PN} += "ldd"
RRECOMMENDS:${PN} = "kernel-module-ext2"

SRCREV = "4ae6a4db762a09dd3c1dfe46030acca6162aef05"
PV = "21.4.9+gitr"

KODIADDONBRANCH = "Omega"

SRC_URI = "git://github.com/xbmc/inputstream.adaptive.git;protocol=https;branch=${KODIADDONBRANCH} \
        file://define-INPUTSTREAM_MAX_STREAM_COUNT-ifndef.patch"

S = "${WORKDIR}/git"

EXTRA_OECMAKE += "-DENABLE_INTERNAL_BENTO4=ON"

# Allow downloads during internals build
do_compile[network] = "1"

KODIADDONNAME = "inputstream.adaptive"

INSANE_SKIP:${PN} = "libdir dev-so"
