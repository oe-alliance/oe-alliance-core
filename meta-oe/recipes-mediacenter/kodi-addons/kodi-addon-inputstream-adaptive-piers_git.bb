SUMMARY = "kodi inputstream addon for several manifest types"

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://src/main.cpp;beginline=1;endline=7;md5=45347fdc3c187ba13a5d93a1db696f47"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

inherit kodi-addon

DEPENDS += "expat nlohmann-json nss pugixml"
RDEPENDS:${PN} += "ldd"
RRECOMMENDS:${PN} = "kernel-module-ext2"

# Keep the published Piers tag used by CoreELEC 22 and LibreELEC.  The Piers
# branch can move past a release without changing the add-on version, which is
# less suitable for a public feed and third-party add-on compatibility.
SRCREV_default = "0801282232050a8d9d67269ada71b88f795c6889"
SRCREV_bento4 = "a4a4172467e592c17c7d823f81196e77e4486f91"
SRCREV_FORMAT = "default_bento4"
PV = "22.3.19+gitr"

KODIADDONBRANCH = "Piers"

SRC_URI = "git://github.com/xbmc/inputstream.adaptive;protocol=https;branch=${KODIADDONBRANCH} \
        git://github.com/xbmc/Bento4.git;protocol=https;nobranch=1;name=bento4;destsuffix=bento4-source \
        file://use-local-bento4-source.patch"

EXTRA_OECMAKE += "-DENABLE_INTERNAL_BENTO4=ON \
    -DBENTO4_SOURCE_DIR=${UNPACKDIR}/bento4-source"

KODIADDONNAME = "inputstream.adaptive"

INSANE_SKIP:${PN} = "libdir dev-so"
