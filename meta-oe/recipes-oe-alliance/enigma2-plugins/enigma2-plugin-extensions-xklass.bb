SUMMARY = "Enigma2 plugin for playing official Xtream Codes IPTV playlists."
HOMEPAGE = "https://www.linuxsat-support.com"
MAINTAINER = "kiddac"
PRIORITY = "optional"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

RDEPENDS:${PN} = "${PYTHON_PN}-pillow ${PYTHON_PN}-requests wget"

inherit gittag allarch

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "${GITPKGVTAG}"
PR = "r1"

SRC_URI = "git://github.com/kiddac/XKlass.git;branch=master;protocol=https"

S = "${WORKDIR}/git"

FILES:${PN} = " ${libdir}/enigma2/python/Components/Converter/* \
                ${libdir}/enigma2/python/Components/Renderer/* \
                ${libdir}/enigma2/python/Plugins/Extensions/XKlass/*"

do_patch[noexec] = "1"

do_configure[noexec] = "1"

do_compile[noexec] = "1"

do_install() {
install -d ${D}${libdir}/enigma2/python/Components/Converter
install -d ${D}${libdir}/enigma2/python/Components/Renderer
install -d ${D}${libdir}/enigma2/python/Plugins/Extensions/XKlass
cp -rf ${S}/XKlass/usr/lib/enigma2/python/Components/Converter/*.py ${D}${libdir}/enigma2/python/Components/Converter/
cp -rf ${S}/XKlass/usr/lib/enigma2/python/Components/Renderer/*.py ${D}${libdir}/enigma2/python/Components/Renderer/
cp -rf ${S}/XKlass/usr/lib/enigma2/python/Plugins/Extensions/XKlass/* ${D}${libdir}/enigma2/python/Plugins/Extensions/XKlass/
}
