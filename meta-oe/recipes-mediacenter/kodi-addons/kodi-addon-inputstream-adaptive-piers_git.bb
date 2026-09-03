SUMMARY = "kodi inputstream addon for several manifest types"

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://src/main.cpp;beginline=1;endline=7;md5=45347fdc3c187ba13a5d93a1db696f47"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

inherit kodi-addon

DEPENDS += "expat nlohmann-json nss pugixml"
RDEPENDS:${PN} += "ldd"
RRECOMMENDS:${PN} = "kernel-module-ext2"

# Track the Piers branch together with Kodi 22 so its binary API and third-party
# add-on properties match the public STB Kodi build.
SRCREV_default = "923ba3475f09d719305c5a6bc711d1d27f66f985"
SRCREV_bento4 = "a4a4172467e592c17c7d823f81196e77e4486f91"
SRCREV_FORMAT = "default_bento4"
PV = "22.3.21+gitr"

KODIADDONBRANCH = "Piers"

SRC_URI = "git://github.com/xbmc/inputstream.adaptive;protocol=https;branch=${KODIADDONBRANCH} \
        git://github.com/xbmc/Bento4.git;protocol=https;nobranch=1;name=bento4;destsuffix=bento4-source \
        file://use-local-bento4-source.patch \
        file://clamp-vod-seek-to-valid-range.patch"

EXTRA_OECMAKE += "-DENABLE_INTERNAL_BENTO4=ON \
    -DBENTO4_SOURCE_DIR=${UNPACKDIR}/bento4-source"

KODIADDONNAME = "inputstream.adaptive"

INSANE_SKIP:${PN} = "libdir dev-so"
