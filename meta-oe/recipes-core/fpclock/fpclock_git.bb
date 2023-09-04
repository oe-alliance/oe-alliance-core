SUMMARY = "Frontpanel Real Time Clock Daemon"
HOMEPAGE = "https://github.com/jbleyel/fpclock"
require conf/license/license-gplv2.inc

RDEPENDS:${PN} += "bash"

inherit autotools gitpkgv update-rc.d

SRCREV = "${AUTOREV}"
PV = "1.0+git"
PKGV = "1.0+git${GITPKGV}"

SRC_URI = "git://github.com/oe-alliance/FPClock;protocol=https;branch=main"

S = "${WORKDIR}/git"

INITSCRIPT_NAME = "fpclock"
INITSCRIPT_PARAMS = "defaults 10"

do_install(){
    install -d ${D}${sbindir}
    install -m 0755 ${B}/src/fpclock ${D}${sbindir}

    install -d ${D}${sysconfdir}/init.d
    install -m 755 ${S}/fpclock.init ${D}${sysconfdir}/init.d/fpclock
}
