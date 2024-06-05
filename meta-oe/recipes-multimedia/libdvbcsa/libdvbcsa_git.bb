SUMMARY = "Open implementation of the DVB Common Scrambling Algorithm, encrypt and decrypt "
SECTION = "libs/multimedia"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "1.1.0+git"
PKGV = "1.1.0+git${GITPKGV}"

SRC_URI = "git://github.com/oe-mirrors/libdvbcsa.git;protocol=https;branch=master \
           file://libdvbcsa.pc \
"

S = "${WORKDIR}/git"

inherit autotools lib_package pkgconfig

TUNE_32_64   = "${@bb.utils.contains("TUNE_FEATURES", "mips64",        "--enable-uint64",  "--enable-uint32", d)}"
TUNE_DVBCSA  = "${@bb.utils.contains_any("TUNE_FEATURES", "neon simd", "--enable-neon",    "${TUNE_32_64}", d)}"

EXTRA_OECONF += "${TUNE_DVBCSA}"

do_install:append() {
    install -d ${D}${includedir}/dvbcsa/
    install -d ${D}${libdir}/pkgconfig
    install -m 0644 ${S}/src/dvbcsa/dvbcsa.h ${D}${includedir}/dvbcsa/
    install -m 0644 ${UNPACKDIR}/libdvbcsa.pc ${D}${libdir}/pkgconfig/
}
