SUMMARY = "kodi inputstream addon for several manifest types"

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://src/main.cpp;beginline=1;endline=7;md5=45347fdc3c187ba13a5d93a1db696f47"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

inherit kodi-addon

DEPENDS += "expat pugixml"
RDEPENDS:${PN} += "ldd"
RRECOMMENDS:${PN} = "kernel-module-ext2"

SRCREV = "358dbacd9ce0e5fd3697c6901ff2ac66af142a7b"
PV = "21.1.2+gitr${SRCPV}"

KODIADDONBRANCH = "Omega"

SRC_URI = "git://github.com/xbmc/inputstream.adaptive.git;protocol=https;branch=${KODIADDONBRANCH} \
        file://define-INPUTSTREAM_MAX_STREAM_COUNT-ifndef.patch"

S = "${WORKDIR}/git"

EXTRA_OECMAKE += "-DENABLE_INTERNAL_BENTO4=ON"

# Allow downloads during internals build
do_compile[network] = "1"

KODIADDONNAME = "inputstream.adaptive"

INSANE_SKIP:${PN} = "libdir dev-so"
