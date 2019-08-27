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

EXTRA_OECMAKE = "-DAAC_OUT=ON -DRTLSDR=ON -DCMAKE_INSTALL_PREFIX=/usr/bin"

S = "${WORKDIR}/git/sdgradio"

inherit cmake pkgconfig

do_configure_prepend() {
	sed -i -e 's:librtlsdr.so:librtlsdr.so.0:g' ${WORKDIR}/git/devices/rtlsdr-handler/rtlsdr-handler.cpp
}
