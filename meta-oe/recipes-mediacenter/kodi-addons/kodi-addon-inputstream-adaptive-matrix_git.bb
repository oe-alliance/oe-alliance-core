SUMMARY = "kodi inputstream addon for several manifest types"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://src/main.cpp;beginline=1;endline=17;md5=5eac1e215251c8f88d799f80ed45d5c0"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

inherit kodi-addon

DEPENDS += "expat"
RDEPENDS_${PN} += "ldd"
RRECOMMENDS_${PN} = "kernel-module-ext2"

SRCREV = "7b0ade1e0bc61743bcd1c36aa2412230ee6e03cd"
PV = "2.6.8+gitr${SRCPV}"

KODIADDONBRANCH = "Matrix"

SRC_URI = "git://github.com/peak3d/inputstream.adaptive.git;protocol=https;branch=${KODIADDONBRANCH} \
        file://define-INPUTSTREAM_MAX_STREAM_COUNT-ifndef.patch"

S = "${WORKDIR}/git"

KODIADDONNAME = "inputstream.adaptive"
