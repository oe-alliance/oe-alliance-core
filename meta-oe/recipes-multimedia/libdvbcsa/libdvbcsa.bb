SUMMARY = "Open implementation of the DVB Common Scrambling Algorithm, encrypt and decrypt "
SECTION = "libs/multimedia"
LICENSE = "LGPLv2.1+"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"

SRCREV = "bc6c0b164a87ce05e9925785cc6fb3f54c02b026"

PV = "1.1.0"
PR = "r2"

SRC_URI = "git://code.videolan.org/videolan/libdvbcsa.git;protocol=https \
           file://libdvbcsa.pc \
"

S = "${WORKDIR}/git"

inherit autotools lib_package pkgconfig

do_install_append() {
    install -d ${D}${includedir}/dvbcsa/
    install -d ${D}${libdir}/pkgconfig
    install -m 0644 ${S}/src/dvbcsa/dvbcsa.h ${D}${includedir}/dvbcsa/
    install -m 0644 ${WORKDIR}/libdvbcsa.pc ${D}${libdir}/pkgconfig/
}