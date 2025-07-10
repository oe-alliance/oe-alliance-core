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

EXTRA_OECMAKE = "-DAAC_OUT=ON -DRAWFILES=ON -DCMAKE_INSTALL_PREFIX=/usr/bin -DCMAKE_POLICY_VERSION_MINIMUM=3.5"

S = "${UNPACKDIR}/${BP}/sdgradio"

inherit cmake pkgconfig

do_configure:prepend() {
    sed -i -e 's:librtlsdr.so:librtlsdr.so.2:g' ${S}/../devices/rtlsdr-handler/rtlsdr-handler.cpp
}
