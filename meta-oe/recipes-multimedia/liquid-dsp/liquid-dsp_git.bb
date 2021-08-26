SUMMARY = "Software-Defined Radio Digital Signal Processing Library"
DESCRIPTION = "liquid-dsp is a free and open-source digital signal processing (DSP) library designed specifically for software-defined radios on embedded platforms."
MAINTAINER = "Joseph D. Gaeddert <joseph@liquidsdr.org>"
HOMEPAGE = "http://liquidsdr.org"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=860e4083ceb93ce0939b1a58fcaacb53"

inherit gitpkgv

SRCREV = "0add775ad4c8a999e3e72228c50e11c1da06d3b5"
PV = "1.3.2a+git${SRCPV}"
PKGV = "1.3.2a+git${GITPKGV}"
PR = "r1"

SRC_URI = "git://github.com/jgaeddert/liquid-dsp.git;protocol=https"

S = "${WORKDIR}/git"

inherit autotools-brokensep

do_install() {
    mkdir -p ${D}${libdir}
    mkdir -p ${D}${includedir}/liquid
    install -m 755 -p ${S}/libliquid.so ${D}${libdir}/libliquid.so.0.0
    ln -s libliquid.so.0.0 ${D}${libdir}/libliquid.so
    ln -s libliquid.so.0.0 ${D}${libdir}/libliquid.so.0
    install -m 755 -p ${S}/libliquid.a ${D}${libdir}
    install -m 644 -p ${S}/include/liquid.h ${D}${includedir}/liquid
}
