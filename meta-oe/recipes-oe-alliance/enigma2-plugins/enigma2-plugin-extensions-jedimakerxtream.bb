DESCRIPTION = "Jedi Maker Xtream (Enigma2 IPTV Bouquet Creator)"

HOMEPAGE = "https://github.com/kiddac/Jedi_Maker_Xtream"
MAINTAINER = "kiddac"
PRIORITY = "optional"
require conf/license/license-gplv2.inc
DEPENDS += "${PYTHON_PN}-backports-lzma"
require conf/python/python3-compileall.inc

SRCREV="${AUTOREV}"

PV = "1.01+git${SRCPV}"
PKGV = "1.01+git${GITPKGV}"
PR = "r2"
inherit gitpkgv allarch

SRC_URI = "git://github.com/kiddac/Jedi_Maker_Xtream.git;protocol=https;branch=master"

S = "${WORKDIR}/git"

FILES:${PN} = " ${sysconfdir}/enigma2/jediplaylists/* \
                ${libdir}/enigma2/python/Plugins/Extensions/JediMakerXtream/*"

do_install () {
    install -d ${D}/${sysconfdir}/enigma2/jediplaylists
    install -d ${D}/${libdir}/enigma2/python/Plugins/Extensions/JediMakerXtream
    cp -rf ${S}/JediMakerXtream//etc/enigma2/jediplaylists/* ${D}/${sysconfdir}/enigma2/jediplaylists/
    cp -rf ${S}/JediMakerXtream/usr/lib/enigma2/python/Plugins/Extensions/JediMakerXtream/* ${D}/${libdir}/enigma2/python/Plugins/Extensions/JediMakerXtream/
}
