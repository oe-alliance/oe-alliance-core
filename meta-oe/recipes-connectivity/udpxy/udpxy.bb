SUMMARY = "udpxy"
MAINTAINER = "Pavel V. Cherenkov"
SECTION = "multimedia"
PRIORITY = "optional"

LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://../README;md5=f210c6f38d8c7db12fdfd009dcd9438c"

inherit gitpkgv update-rc.d

SRCREV = "${AUTOREV}"
PV = "1+git"
PKGV = "1+git${GITPKGV}"

SRC_URI = "git://github.com/pcherenkov/udpxy.git;protocol=https;branch=master \
           file://udpxy.sh"

CFLAGS:append = " -Wno-format-truncation "

S = "${UNPACKDIR}/udpxy-1+git/chipmunk"

FILES:${PN} = "${bindir}/* /etc/init.d/udpxy.sh"

do_configure[noexec] = "1"

do_compile() {
    oe_runmake -f Makefile udpxy
}

do_install() {
    install -d ${D}${bindir}
    install -m 755 ${S}/udpxy ${D}${bindir}

    install -d ${D}/etc/init.d
    install -m 755 ${UNPACKDIR}/udpxy.sh ${D}/etc/init.d/
}

INITSCRIPT_NAME = "udpxy.sh"
INITSCRIPT_PARAMS = "defaults"
