SUMMARY = "PC/SC Lite smart card framework and applications"
HOMEPAGE = "http://pcsclite.alioth.debian.org/"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=628c01ba985ecfa21677f5ee2d5202f6"

DEPENDS = "libusb1 python"
RDEPENDS_${PN} = "libusb1 python"
RRECOMMENDS_${PN} = "ccid"

PACKAGES =+ "${PN}-lib"

SRC_URI = "https://pcsclite.apdu.fr/files/pcsc-lite-${PV}.tar.bz2 \
    file://pcscd.init"

SRC_URI[md5sum] = "c20650a36062ab1689f37f3302c988f2"
SRC_URI[sha256sum] = "d76d79edc31cf76e782b9f697420d3defbcc91778c3c650658086a1b748e8792"

S = "${WORKDIR}/pcsc-lite-${PV}"

inherit autotools pkgconfig update-rc.d

INITSCRIPT_NAME = "pcscd"
INITSCRIPT_PARAMS = "defaults"

EXTRA_OECONF = " \
    --disable-libudev \
    --disable-libsystemd \
    --enable-libusb \
    --enable-usbdropdir=${libdir}/pcsc/drivers \
    "

do_install() {
    oe_runmake DESTDIR="${D}" install
    install -d "${D}/etc/init.d"
    install -m 755 "${WORKDIR}/pcscd.init" "${D}/etc/init.d/pcscd"
}

FILES_${PN}-lib = "${libdir}/lib*${SOLIBS}"
