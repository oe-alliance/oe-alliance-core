SUMMARY = "Betacentauris couch flashing"
MAINTAINER = "Betacentauri"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=0636e73ff0215e8d672dc4c32c317bb3 \
                    file://include/common.h;beginline=1;endline=17;md5=ba05b07912a44ea2bf81ce409380049c"

inherit gitpkgv
SRCREV = "${AUTOREV}"
PKGV = "4.x+git${GITPKGV}"
PV = "4.x+git${SRCPV}"

SRC_URI = "git://github.com/oe-alliance/ofgwrite.git \
    file://fix_glibc_major.patch \
"

inherit autotools-brokensep pkgconfig

S = "${WORKDIR}/git"

EXTRA_OEMAKE = "'CC=${CC}' 'RANLIB=${RANLIB}' 'AR=${AR}' 'CFLAGS=${CFLAGS} -I${S}/include -I${S}/ubi-utils/include -I${S}/busybox/include -I=${includedir}/glib-2.0 -I=/usr/lib/glib-2.0/include -I=${includedir}/c++ -I=${includedir}/c++/mipsel-oe-linux -DWITHOUT_XATTR -D_FILE_OFFSET_BITS=64 -D_GNU_SOURCE' 'BUILDDIR=${S}'"

do_install() {
    install -d ${D}/usr/bin
    install -m 755 ${S}/ofgwrite ${D}/usr/bin
    install -m 755 ${S}/ofgwrite_bin ${D}/usr/bin
    install -m 755 ${S}/ofgwrite_test ${D}/usr/bin
}
