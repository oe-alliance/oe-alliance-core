SUMMARY = "direct Backup for Enigma2"
MAINTAINER = "gutemine <gutemine@unknown.com>"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
inherit allarch
DEPENDS = "enigma2"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "0.1+git${SRCPV}"
PKGV = "0.1+git${GITPKGV}"
PR = "r0"

SRC_URI="git://github.com/opendroid-Team/dbackup.git"

S = "${WORKDIR}/git"

FILES_${PN} = "/usr/*"

do_install() {
    cp -rp ${S}/usr ${D}/
    chmod -R 777 ${D}/usr/lib/enigma2/python/Plugins/Extensions/dBackup
}

pkg_postinst_${PN}() {
#!/bin/sh
rm usr/lib/enigma2/python/Plugins/Extensions/WebInterface/WebChilds/External/dBackup* > /dev/null 2>&1
rm usr/lib/enigma2/python/Plugins/Extensions/WebInterface/WebChilds/External/dBackup* > /dev/null 2>&1
exit 0
}

pkg_postrm_${PN}() {
#!/bin/sh
rm /usr/lib/enigma2/python/Plugins/Extensions/WebInterface/web-data/img/dBackup.png > /dev/null 2>&1
rm /usr/lib/enigma2/python/Plugins/Extensions/WebInterface/web-data/img/ring.png > /dev/null 2>&1
rm -r /usr/lib/enigma2/python/Plugins/Extensions/dBackup > /dev/null 2>&1
exit 0
}
