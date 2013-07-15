DESCRIPTION = "PC/SC Lite smart card framework and applications"
HOMEPAGE = "http://pcsclite.alioth.debian.org/"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=a39d325b7d9cf2f07826a5154b16500c"
DEPENDS = "udev"
PR = "r0"

SRC_URI = "https://alioth.debian.org/frs/download.php/3862/pcsc-lite-${PV}.tar.bz2"

SRC_URI[md5sum] = "069dc875a2ae2d85a2ebceac73252c0a"
SRC_URI[sha256sum] = "fe66354a7e738d3ef8b4e572c7e447b85894da9262381fbf004e8abcc12885e7"

inherit autotools

EXTRA_OECONF = " \
	--disable-libusb \
	--enable-libudev \
	--enable-usbdropdir=${libdir}/pcsc/drivers \
	"

S = "${WORKDIR}/pcsc-lite-${PV}"

PACKAGES =+ "${PN}-lib"

RRECOMMENDS_${PN} = "ccid"

FILES_${PN}-lib = "${libdir}/lib*${SOLIBS}"