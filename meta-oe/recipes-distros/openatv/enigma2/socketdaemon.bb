SUMMARY = "enigma2 socketdaemon for start/stop services"
SECTION = "base"
PRIORITY = "required"
MAINTAINER = "openATV"

require conf/license/license-gplv2.inc

inherit gitpkgv autotools update-rc.d

SRCREV = "${AUTOREV}"
PV = "1.2+git"
PKGV = "1.2+git${GITPKGV}"
VER = "1.2"
PR = "r0"

SRC_URI = "git://github.com/openatv/socketdaemon.git;branch=8.0;protocol=https"

B = "${WORKDIR}/build"

INITSCRIPT_NAME = "${PN}"
INITSCRIPT_PARAMS = "defaults 60"

FILES:${PN} = "/"

do_install() {
    install -d ${D}/usr/bin
    install -m 0755 ${B}/socketdaemon ${D}/usr/bin/socketdaemon
    install -d ${D}/etc/init.d
    install -m 0755 ${S}/${PN}.sh ${D}/etc/init.d/${PN}
}
