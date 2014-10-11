SUMMARY = "GigaBlue IPBox Server"
MAINTAINER = "GigaBlue"
SECTION = "base"
LICENSE = "proprietary"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
VER ="1.0"
PR = "r0"

INITSCRIPT_NAME = "gbipboxserver"
INITSCRIPT_PARAMS = "defaults 20"

inherit update-rc.d

SRC_URI="git://github.com/openmips/gbremote-server.git"

S = "${WORKDIR}/git"

FILES_${PN} = "/etc /usr"

do_install() {
    cp -rp ${S}/etc ${D}/
    cp -rp ${S}/usr ${D}/
    chmod +x ${D}/etc/init.d/gbipboxserver
    chmod +x ${D}/usr/bin/gbipbox_server
}
