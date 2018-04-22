SUMMARY = "DAB decoding library with example of its use"
SECTION = "libs/multimedia"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://../license;md5=427d5433a7bd7fc1e38dc15e93cbc645"

SRCREV = "${AUTOREV}"

DEPENDS = "fftw libusb1 faad2 zlib rtl-sdr"
RDEPENDS_${PN} = "rtl-sdr"

inherit gitpkgv

PV = "1.2+git${SRCPV}"
PKGV = "1.2+git${GITPKGV}"
PR = "r0"

SRC_URI = "git://github.com/satdreamgr/dab-cmdline.git;branch=sdgradio"

NEON_cube =" "
NEON = "-DNEON_AVAILABLE=ON"

EXTRA_OECMAKE ?= "-DRTLSDR=ON -DCMAKE_INSTALL_PREFIX=/usr/bin"
EXTRA_OECMAKE_arm = "-DRTLSDR=ON ${NEON} -DCMAKE_INSTALL_PREFIX=/usr/bin"

S = "${WORKDIR}/git/sdgradio"

inherit cmake pkgconfig

do_configure_prepend() {
	sed -i -e 's:librtlsdr.so:librtlsdr.so.0:g' ${WORKDIR}/git/devices/rtlsdr-handler/rtlsdr-handler.cpp
}

do_install_append() {
	mv ${D}/usr/bin/dab-rtlsdr-sdgradio ${D}/usr/bin/dab-rtlsdr-sdgradio-pcm
}
