SUMMARY = "IPTV Ministra stalker player by KiddaC"
HOMEPAGE = "https://www.linuxsat-support.com"
MAINTAINER = "kiddac"
PRIORITY = "optional"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

RDEPENDS:${PN} = "gstplayer gstplayer2 enigma2-plugin-systemplugins-serviceapp ${PYTHON_PN}-pillow ${PYTHON_PN}-multiprocessing ${PYTHON_PN}-requests"

inherit gittag allarch

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "${GITPKGVTAG}"
PR = "r0"

SRC_URI = "git://github.com/kiddac/EStalker.git;branch=master;protocol=https"

FILES:${PN} = " ${libdir}/enigma2/python/Components/Converter/* \
                ${libdir}/enigma2/python/Components/Renderer/* \
                ${libdir}/enigma2/python/Plugins/Extensions/EStalker/*"

do_patch[noexec] = "1"

do_configure[noexec] = "1"

do_compile[noexec] = "1"

do_install() {
install -d ${D}${libdir}/enigma2/python/Components/Converter
install -d ${D}${libdir}/enigma2/python/Components/Renderer
install -d ${D}${libdir}/enigma2/python/Plugins/Extensions/EStalker
cp -rf ${S}/EStalker/usr/lib/enigma2/python/Components/Converter/*.py ${D}${libdir}/enigma2/python/Components/Converter/
cp -rf ${S}/EStalker/usr/lib/enigma2/python/Components/Renderer/*.py ${D}${libdir}/enigma2/python/Components/Renderer/
cp -rf ${S}/EStalker/usr/lib/enigma2/python/Plugins/Extensions/EStalker/* ${D}${libdir}/enigma2/python/Plugins/Extensions/EStalker/
}
