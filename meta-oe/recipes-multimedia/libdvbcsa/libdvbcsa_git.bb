SUMMARY = "Open implementation of the DVB Common Scrambling Algorithm, encrypt and decrypt "
SECTION = "libs/multimedia"
LICENSE = "LGPL-2.1-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "1.1.0+git${SRCPV}"
PKGV = "1.1.0+git${GITPKGV}"

SRC_URI = "git://github.com/glenvt18/libdvbcsa.git;protocol=https;branch=master \
           file://libdvbcsa.pc \
           file://emm.patch \
"

SRC_URI[sha256sum] = "c78b61f83a8b7542b5a91164398aa2809d2ea9926488002653e3776a26f4c17b"

S = "${WORKDIR}/git"

#libdvbcsa-32 mipsel, arm w/o Neon --enable-uint32
#libdvbcsa-64 aarch64 w/o Neon --enable-uint64
#libdvbcsa-128 arm+aarch64 with Neon --enable-neon

inherit autotools lib_package pkgconfig

CONFIGUREOPTS:arm = " --host=${HOST_SYS} --build=${BUILD_SYS} --prefix=${prefix} --enable-neon ${PACKAGECONFIG_CONFARGS}"
CONFIGUREOPTS:mipsel = " --host=${HOST_SYS} --build=${BUILD_SYS} --prefix=${prefix} --enable-uint32 ${PACKAGECONFIG_CONFARGS}"
CONFIGUREOPTS:sh4 = " --host=${HOST_SYS} --build=${BUILD_SYS} --prefix=${prefix} --enable-uint32 ${PACKAGECONFIG_CONFARGS}"

do_install:append() {
    install -d ${D}${includedir}/dvbcsa/
    install -d ${D}${libdir}/pkgconfig
    install -m 0644 ${S}/src/dvbcsa/dvbcsa.h ${D}${includedir}/dvbcsa/
    install -m 0644 ${WORKDIR}/libdvbcsa.pc ${D}${libdir}/pkgconfig/
}
