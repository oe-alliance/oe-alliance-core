SUMMARY = "Open implementation of the DVB Common Scrambling Algorithm, encrypt and decrypt "
SECTION = "libs/multimedia"
LICENSE = "LGPL-2.1-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "1.1.0+git${SRCPV}"
PKGV = "1.1.0+git${GITPKGV}"

SRC_URI = "git://github.com/oe-mirrors/libdvbcsa.git;protocol=https;branch=master \
           file://libdvbcsa.pc \
"

S = "${WORKDIR}/git"

inherit autotools lib_package pkgconfig

EXTRA_OECONF += " \
           ${@bb.utils.contains("TUNE_FEATURES", "altivec", "--enable-altivec", \
           ${@bb.utils.contains("TUNE_FEATURES", "avx2",    "--enable-avx2", \
           ${@bb.utils.contains("TUNE_FEATURES", "ssse3",   "--enable-ssse3", \
           ${@bb.utils.contains("TUNE_FEATURES", "sse2",    "--enable-sse2", \
           ${@bb.utils.contains("TUNE_FEATURES", "mmx",     "--enable-mmx", \
           ${@bb.utils.contains("TUNE_FEATURES", "neon",    "--enable-neon", \
           ${@bb.utils.contains("TUNE_FEATURES", "mips64",  "--enable-uint64", "--enable-uint32", d)})})})})})})} \
"

do_install:append() {
    install -d ${D}${includedir}/dvbcsa/
    install -d ${D}${libdir}/pkgconfig
    install -m 0644 ${S}/src/dvbcsa/dvbcsa.h ${D}${includedir}/dvbcsa/
    install -m 0644 ${WORKDIR}/libdvbcsa.pc ${D}${libdir}/pkgconfig/
}
