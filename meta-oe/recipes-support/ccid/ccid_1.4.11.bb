SUMMARY = "Generic USB CCID smart card reader driver"
HOMEPAGE = "http://pcsclite.alioth.debian.org/ccid.html"
LICENSE = "LGPLv2.1+"
LIC_FILES_CHKSUM = "file://COPYING;md5=2d5025d4aa3495befef8f17206a5b0a1"
PR = "r2"

DEPENDS = "virtual/libusb0 pcsc-lite"
RDEPENDS_${PN} = "pcsc-lite"

SRC_URI = "https://alioth.debian.org/frs/download.php/file/3920/ccid-${PV}.tar.bz2"

SRC_URI[md5sum] = "20e3d6f045c53707a597cbacb86b6c5b"
SRC_URI[sha256sum] = "04ce7ebc7f591b7542e15d4dcd1c50900d0d86c0639893832f5d6144ffb3c369"

inherit autotools pkgconfig

FILES_${PN} += "${libdir}/pcsc/"
FILES_${PN}-dbg += "${libdir}/pcsc/drivers/*/*/*/.debug"