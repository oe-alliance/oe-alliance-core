SUMMARY = "idle3-tools"
MAINTAINER = "idle3-tools"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=8f0e2cd40e05189ec81232da84bd6e1a"

PR = "r1"

inherit autotools-brokensep pkgconfig

SRC_URI="http://source.mynonpublic.com/idle3-tools-0.9.1.tgz"

S = "${WORKDIR}/${BPN}-${PV}"

EXTRA_OEMAKE = "'CC=${CC}' 'RANLIB=${RANLIB}' 'AR=${AR}' 'CFLAGS=${CFLAGS} -I${S}/include -I${S}/ubi-utils/include -I=${includedir}/glib-2.0 -I=/usr/lib/glib-2.0/include -I=${includedir}/c++ -I=${includedir}/c++/mipsel-oe-linux -DWITHOUT_XATTR' 'BUILDDIR=${S}'"

do_compile() {
   make -f ${S}/Makefile idle3ctl
}

do_install() {
    install -d ${D}/usr/bin
    install -m 755 ${S}/idle3ctl ${D}/usr/bin
}

SRC_URI[md5sum] = "797d8775b80b7b7b67a1f8b0a5b41f30"
SRC_URI[sha256sum] = "9778060c7873ad2c9c40db3a7049d8ca22535427b982ff12f5bd519f703f2a02"

INSANE_SKIP_${PN} += "already-stripped ldflags"