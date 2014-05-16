SUMMARY = "direct Flashing and Backup for Enigma2"
MAINTAINER = "gutemine <gutemine@unknown.com>"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
PACKAGE_ARCH = "all"
DEPENDS = "enigma2"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "12.6+git${SRCPV}"
PKGV = "12.6+git${GITPKGV}"
VER ="12.6"
PR = "r0"

SRC_URI="git://github.com/openatv/enigma2-plugin-extensions-dflash.git"

S = "${WORKDIR}/git"

FILES_${PN} = "/usr/*"

do_install() {
    cp -rp ${S}/usr ${D}/
    chmod -R 777 ${D}/usr/lib/enigma2/python/Plugins/Extensions/dFlash
}

pkg_postinst_${PN}() {
#!/bin/sh
rm usr/lib/enigma2/python/Plugins/Extensions/WebInterface/WebChilds/External/dFlash* > /dev/null 2>&1
rm usr/lib/enigma2/python/Plugins/Extensions/WebInterface/WebChilds/External/dflash* > /dev/null 2>&1
if grep -qs dm7025 /proc/stb/info/model -o grep -qs dm800 /proc/stb/info/model; then
   rm /usr/lib/enigma2/python/Plugins/Extensions/dFlash/bin/mkfs.ubifs > /dev/null 2>&1
   touch /usr/lib/enigma2/python/Plugins/Extensions/dFlash/bin/mkfs.ubifs > /dev/null 2>&1
fi                                            
exit 0
}

pkg_postrm_${PN}() {
#!/bin/sh
rm /usr/lib/enigma2/python/Plugins/Extensions/WebInterface/web-data/img/dflash.png > /dev/null 2>&1
rm /usr/lib/enigma2/python/Plugins/Extensions/WebInterface/web-data/img/ring.png > /dev/null 2>&1
rm -r /usr/lib/enigma2/python/Plugins/Extensions/dFlash > /dev/null 2>&1
exit 0
}