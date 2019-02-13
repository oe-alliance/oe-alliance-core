SUMMARY = "rtmpdump Real-Time Messaging Protocol"

require conf/license/license-gplv2.inc


DEPENDS = "openssl zlib"
RDEPENDS_${PN} = "librtmp"

inherit autotools-brokensep gitpkgv

SRCREV = "${AUTOREV}"
PKGV = "2.48+git${GITPKGV}"
PV = "2.48+git${SRCPV}"

SRC_URI = "git://github.com/oe-alliance/rtmpdump.git;protocol=git \
        file://fix-build-openssl102q.patch \
        file://fix-build-openssl111a.patch"

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
