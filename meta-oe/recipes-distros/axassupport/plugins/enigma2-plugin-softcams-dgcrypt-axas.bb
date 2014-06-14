SUMMARY = "enigma2-plugin-softcams-dgcrypt-axas"
MAINTAINER = "axas"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
VER ="1.0"
PR = "r1"

SRC_URI="git://github.com/AXAS/enigma2-plugin-softcams-dgcrypt-axas.git"

S = "${WORKDIR}/git"

FILES_${PN} = "/usr/* /etc/*"

do_install() {
    cp -rp ${S}/usr ${D}/
	cp -rp ${S}/etc ${D}/
    chmod 777 ${D}/etc/init.d/softcam.dgcrypt
    chmod 777 ${D}/usr/bin/dgcrypt
}