SUMMARY = "PC/SC Lite smart card framework and applications"
HOMEPAGE = "http://pcsclite.alioth.debian.org/"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=a39d325b7d9cf2f07826a5154b16500c"
DEPENDS = "libusb1 python"
RDEPENDS_${PN} = "libusb1 python"
RRECOMMENDS_${PN} = "ccid"

PACKAGES =+ "${PN}-lib"
PR = "r4"

SRC_URI = "http://sources.openelec.tv/mirror/pcsc-lite/pcsc-lite-1.8.8.tar.bz2 \
    file://pcscd.init"

SRC_URI[md5sum] = "069dc875a2ae2d85a2ebceac73252c0a"
SRC_URI[sha256sum] = "fe66354a7e738d3ef8b4e572c7e447b85894da9262381fbf004e8abcc12885e7"

inherit autotools pkgconfig update-rc.d

INITSCRIPT_NAME = "pcscd"
INITSCRIPT_PARAMS = "defaults"

EXTRA_OECONF = " \
    --disable-libudev \
    --enable-libusb \
    --enable-usbdropdir=${libdir}/pcsc/drivers \
    "

do_install() {
    oe_runmake DESTDIR="${D}" install
    install -d "${D}/etc/init.d"
    install -m 755 "${WORKDIR}/pcscd.init" "${D}/etc/init.d/pcscd"
}

S = "${WORKDIR}/pcsc-lite-${PV}"

FILES_${PN}-lib = "${libdir}/lib*${SOLIBS}"