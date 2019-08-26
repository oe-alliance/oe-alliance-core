SUMMARY = "direct Flashing and Backup for Enigma2"
MAINTAINER = "gutemine <gutemine@oozoon.de>"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"

RDEPENDS_${PN} = "dreambox-buildimage"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "13.8+git${SRCPV}"
PKGV = "13.8+git${GITPKGV}"
VER ="13.8"
PR = "r0"

SRC_URI="git://github.com/openatv/enigma2-plugin-extensions-dflash.git"

S = "${WORKDIR}/git"

FILES_${PN} = "${libdir} ${sbindir}"

do_install() {
    install -d ${D}${libdir}
    cp -rp ${S}${libdir}/* ${D}${libdir}/
    install -d ${D}${sbindir}
    install -m 0755 ${S}/bin/nand_check ${D}${sbindir}/nand_check
    install -m 0755 ${S}/bin/nfiwrite ${D}${sbindir}/nfiwrite
}

pkg_postrm_${PN}() {
#!/bin/sh
rm ${libdir}/enigma2/python/Plugins/Extensions/WebInterface/web-data/img/dflash.png > /dev/null 2>&1
rm ${libdir}/enigma2/python/Plugins/Extensions/WebInterface/web-data/img/ring.png > /dev/null 2>&1
rm -r ${libdir}/enigma2/python/Plugins/Extensions/dFlash > /dev/null 2>&1
exit 0
}

do_package_qa[noexec] = "1"
