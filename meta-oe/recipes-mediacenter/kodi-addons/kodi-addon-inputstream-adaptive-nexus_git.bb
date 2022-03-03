SUMMARY = "kodi inputstream addon for several manifest types"

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://src/main.cpp;beginline=1;endline=17;md5=5eac1e215251c8f88d799f80ed45d5c0"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

inherit kodi-addon

DEPENDS += "expat bento4"
RDEPENDS:${PN} += "ldd"
RRECOMMENDS:${PN} = "kernel-module-ext2"

SRCREV = "80da276cd7efd2ee6f669a55b269d65a178b6dab"
PV = "20.1.2+gitr${SRCPV}"

KODIADDONBRANCH = "Nexus"

SRC_URI = "git://github.com/xbmc/inputstream.adaptive.git;protocol=https;branch=${KODIADDONBRANCH} \
        file://define-INPUTSTREAM_MAX_STREAM_COUNT-ifndef.patch"

S = "${WORKDIR}/git"

KODIADDONNAME = "inputstream.adaptive"

INSANE_SKIP:${PN} = "libdir dev-so"
