SUMMARY = "librtmp Real-Time Messaging Protocol API"
LICENSE = "LGPLv2"

LIC_FILES_CHKSUM = "file://COPYING;md5=e344c8fa836c3a41c4cbd79d7bd3a379"

DEPENDS = "openssl zlib"

PROVIDES =+ " librtmp1"
PACKAGES =+ " librtmp1"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PKGV = "2.48+git${GITPKGV}"
PV = "2.48+git${SRCPV}"
PR = "r4"

SRC_URI = "git://github.com/oe-alliance/rtmpdump.git;protocol=git"

S = "${WORKDIR}/git/librtmp"

do_compile() {
    oe_runmake CROSS_COMPILE=${TARGET_PREFIX} CFLAGS="${CFLAGS} -fPIC" LDFLAGS="${LDFLAGS}"
}

do_install() {
    install -d ${D}${libdir}
    oe_runmake DESTDIR=${D} install
}
