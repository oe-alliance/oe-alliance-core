SUMMARY = "Standalone IPTV browser and player for Enigma2"
DESCRIPTION = "GT IPTV Player Pro provides Xtream Codes, M3U and Stalker/MAC portal browsing and playback."
HOMEPAGE = "https://github.com/VicTuS59/GT-IPTV-Player-Pro"
MAINTAINER = "VicTuS59"
PRIORITY = "optional"

require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

inherit allarch

SRC_URI = "git://github.com/VicTuS59/GT-IPTV-Player-Pro.git;protocol=https;branch=main"
SRCREV = "8ce3454e293247263879f34b22df3144a8a0ecb3"

PV = "1.0.0"
PR = "r0"

S = "${UNPACKDIR}/git"

RDEPENDS:${PN} = "\
    enigma2 \
    ${PYTHON_PN}-core \
    ${PYTHON_PN}-crypt \
    ${PYTHON_PN}-datetime \
    ${PYTHON_PN}-difflib \
    ${PYTHON_PN}-html \
    ${PYTHON_PN}-io \
    ${PYTHON_PN}-json \
    ${PYTHON_PN}-netclient \
    ${PYTHON_PN}-stringold \
    ${PYTHON_PN}-threading \
"

PLUGINPATH = "${libdir}/enigma2/python/Plugins/Extensions/GTIPTVPlayerPro"

FILES:${PN} = "${PLUGINPATH}"

do_patch[noexec] = "1"
do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
    install -d ${D}${PLUGINPATH}
    cp -r --no-preserve=ownership ${S}/GTIPTVPlayerPro/. ${D}${PLUGINPATH}/
    chmod -R a+rX ${D}${PLUGINPATH}
}

pkg_postrm:${PN}() {
#!/bin/sh
plugin_dir="${PLUGINPATH}"
if [ ! -L "$plugin_dir" ] && [ -d "$plugin_dir" ]; then
    rm -rf "$plugin_dir"
fi
exit 0
}
