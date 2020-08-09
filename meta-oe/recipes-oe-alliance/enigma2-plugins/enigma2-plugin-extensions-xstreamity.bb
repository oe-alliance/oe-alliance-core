SUMMARY = "IPTV Xtream Codes playlists player by KiddaC"
HOMEPAGE = "https://www.linuxsat-support.com"
MAINTAINER = "kiddac"
PRIORITY = "optional"
require conf/license/license-gplv2.inc

RDEPENDS_${PN} = "${@bb.utils.contains("PYTHON_PN", "python", "${PYTHON_PN}-imaging python-image", "${PYTHON_PN}-pillow", d)} ${PYTHON_PN}-multiprocessing ${PYTHON_PN}-requests"

SRCREV = "${AUTOREV}"

PV = "2.26+git${SRCPV}"
PKGV = "2.26+git${GITPKGV}"
PR = "r0"

inherit gitpkgv allarch

SRC_URI = "git://github.com/kiddac/XStreamity.git;protocol=http"

S = "${WORKDIR}/git"

FILES_${PN} = " ${libdir}/enigma2/python/Components/Converter/* \
                ${libdir}/enigma2/python/Components/Renderer/* \
                ${libdir}/enigma2/python/Plugins/Extensions/XStreamity/*"

do_patch[noexec] = "1"

do_configure[noexec] = "1"

do_compile[noexec] = "1"

do_install() {
install -d ${D}${libdir}/enigma2/python/Components/Converter
install -d ${D}${libdir}/enigma2/python/Components/Renderer
install -d ${D}${libdir}/enigma2/python/Plugins/Extensions/XStreamity
cp -rf ${S}/XStreamity/usr/lib/enigma2/python/Components/Converter/*.py ${D}${libdir}/enigma2/python/Components/Converter/
cp -rf ${S}/XStreamity/usr/lib/enigma2/python/Components/Renderer/*.py ${D}${libdir}/enigma2/python/Components/Renderer/
cp -rf ${S}/XStreamity/usr/lib/enigma2/python/Plugins/Extensions/XStreamity/* ${D}${libdir}/enigma2/python/Plugins/Extensions/XStreamity/
}

pkg_preinst_${PN}() {
#!/bin/sh
if [ -f "/etc/enigma2/X-Streamity/playlists.json" ]
	then
	rm -f /etc/enigma2/X-Streamity/playlists.json > /dev/null 2>&1
fi

if [ -f "/etc/enigma2/xstreamity/playlists.json" ]
	then
	rm -f /etc/enigma2/xstreamity/playlists.json > /dev/null 2>&1
fi

if [ -f "/etc/enigma2/xstreamity/x-playlists.json" ]
	then
	rm -f /etc/enigma2/xstreamity/x-playlists.json > /dev/null 2>&1
fi
}
