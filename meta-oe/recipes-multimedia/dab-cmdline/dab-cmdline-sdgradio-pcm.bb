SUMMARY = "DAB decoding library with example of its use"
SECTION = "libs/multimedia"
LICENSE = "GPL-2.0-only"
require conf/license/license-gplv2.inc

SRCREV = "${AUTOREV}"

DEPENDS = "fftw libusb1 faad2 zlib rtl-sdr"
RDEPENDS:${PN} = "rtl-sdr"

inherit gitpkgv

PV = "1.3+git"
PKGV = "1.3+git${GITPKGV}"
PR = "r1"

SRC_URI = "git://github.com/satdreamgr/dab-cmdline.git;protocol=https;branch=sdgradio"

NEON = "-DNEON_AVAILABLE=ON"

EXTRA_OECMAKE ?= "-DRTLSDR=ON -DCMAKE_INSTALL_PREFIX=/usr/bin"
EXTRA_OECMAKE:arm = "-DRTLSDR=ON ${NEON} -DCMAKE_INSTALL_PREFIX=/usr/bin"

S = "${WORKDIR}/git/sdgradio"

inherit cmake pkgconfig

do_configure:prepend() {
    sed -i -e 's:librtlsdr.so:librtlsdr.so.2:g' ${WORKDIR}/git/devices/rtlsdr-handler/rtlsdr-handler.cpp
}

do_install:append() {
    mv ${D}/usr/bin/dab-rtlsdr-sdgradio ${D}/usr/bin/dab-rtlsdr-sdgradio-pcm
}
