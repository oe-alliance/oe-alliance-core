SUMMARY = "kodi inputstream addon for several manifest types"

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://src/main.cpp;beginline=1;endline=7;md5=45347fdc3c187ba13a5d93a1db696f47"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

inherit kodi-addon

DEPENDS += "expat bento4"
RDEPENDS:${PN} += "ldd"
RRECOMMENDS:${PN} = "kernel-module-ext2"

SRCREV = "3e55aa75e2a785e8a3c872004d568b5659f916d4"
PV = "20.2.2+gitr${SRCPV}"

KODIADDONBRANCH = "Nexus"

SRC_URI = "git://github.com/xbmc/inputstream.adaptive.git;protocol=https;branch=${KODIADDONBRANCH} \
        file://define-INPUTSTREAM_MAX_STREAM_COUNT-ifndef.patch"

S = "${WORKDIR}/git"

KODIADDONNAME = "inputstream.adaptive"

INSANE_SKIP:${PN} = "libdir dev-so"
