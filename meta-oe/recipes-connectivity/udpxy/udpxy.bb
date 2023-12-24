SUMMARY = "udpxy"
MAINTAINER = "Pavel V. Cherenkov"
SECTION = "multimedia"
PRIORITY = "optional"
LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://README;md5=f210c6f38d8c7db12fdfd009dcd9438c"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "1+git"
PKGV = "1+git${GITPKGV}"

inherit autotools-brokensep pkgconfig

SRC_URI = "git://github.com/pcherenkov/udpxy.git;protocol=https;branch=master file://udpxy.sh"
CFLAGS:append = " -Wno-format-truncation "

S = "${WORKDIR}/git/chipmunk"

FILES:${PN} = "${bindir}/* /etc/init.d/udpxy.sh"

do_compile() {
    make -f Makefile udpxy
}

do_install() {
    install -d ${D}/etc/init.d
    install -m 755 ${WORKDIR}/udpxy.sh ${D}/etc/init.d/
    install -d ${D}/${bindir}
    install -m 755 ${S}/udpxy ${D}/${bindir}
}

INITSCRIPT_NAME = "udpxy.sh"
INITSCRIPT_PARAMS = "defaults"

inherit update-rc.d
