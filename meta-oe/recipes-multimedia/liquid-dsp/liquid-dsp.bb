SUMMARY = "Software-Defined Radio Digital Signal Processing Library"
DESCRIPTION = "liquid-dsp is a free and open-source digital signal processing (DSP) library designed specifically for software-defined radios on embedded platforms."
MAINTAINER = "Joseph D. Gaeddert <joseph@liquidsdr.org>"
HOMEPAGE = "http://liquidsdr.org"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=860e4083ceb93ce0939b1a58fcaacb53"

inherit gitpkgv

PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"

SRC_URI = "git://github.com/jgaeddert/liquid-dsp.git;protocol=http"
SRCREV = "8c1978fa4f5662b8849fe712be716958f29cec0e"

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
