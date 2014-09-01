SUMMARY = "rtmpdump Real-Time Messaging Protocol"

require conf/license/license-gplv2.inc


DEPENDS = "openssl zlib"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PKGV = "2.48+git${GITPKGV}"
PV = "2.48+git${SRCPV}"
PR = "r4"

SRC_URI = "git://github.com/oe-alliance/rtmpdump.git;protocol=git"

S = "${WORKDIR}/git"

do_compile() {
    oe_runmake CROSS_COMPILE=${TARGET_PREFIX} CFLAGS="${CFLAGS} -fPIC" LDFLAGS="${LDFLAGS}"
}


do_install() {
    install -d ${D}${bindir}
        install rtmpdump ${D}${bindir}/
}
