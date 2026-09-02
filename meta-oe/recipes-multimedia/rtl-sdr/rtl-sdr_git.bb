SUMMARY = "Software to turn the RTL2832U into an SDR"
DESCRIPTION = "DVB-T dongles based on the Realtek RTL2832U can be used as a cheap SDR, since the chip allows transferring the raw I/Q samples to the host, which is officially used for DAB/DAB+/FM demodulation"
HOMEPAGE = "https://github.com/old-dab/rtlsdr"
MAINTAINER = "https://github.com/old-dab/rtlsdr"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe"

DEPENDS = "libusb1"

inherit gitpkgv

SRCREV = "64ff2ac8e9ecf35af28e1ccdf86d842037da3133"
PV = "2.0.3+old-dab+git"
PKGV = "2.0.3+old-dab+git${GITPKGV}"

SRC_URI = "git://github.com/old-dab/rtlsdr.git;protocol=https;branch=master \
           file://0001-preserve-librtlsdr-soname.patch \
           file://0002-do-not-strip-while-linking.patch \
"

EXTRA_OECMAKE += " -DDETACH_KERNEL_DRIVER=ON -DLIB_INSTALL_DIR=${libdir}"

inherit cmake pkgconfig
