SUMMARY = "rtmpdump Real-Time Messaging Protocol"

require conf/license/license-gplv2.inc


DEPENDS = "openssl zlib"

inherit autotools-brokensep gitpkgv

SRCREV = "${AUTOREV}"
PKGV = "${GITPKGVTAG}"
PV = "2.48+git${SRCPV}"
PR = "r6"

SRC_URI = "git://github.com/oe-alliance/rtmpdump.git;protocol=git"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = " \
    CC='${CC}' LD='${LD} ${STAGING_LIBDIR}' \
    SYS=posix INC=-I=/usr/include DESTDIR=${D} \
    prefix=${prefix} libdir=${libdir} incdir=${includedir}/librtmp bindir=${bindir} mandir=${mandir}"


do_install() {
    install -d ${D}${bindir}
        install rtmpdump ${D}${bindir}/
}

INSANE_SKIP_${PN} += "ldflags"
