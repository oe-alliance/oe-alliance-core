FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

DEPENDS += "libusb1"
RDEPENDS:${PN} += "libusb1"

SRC_URI:append = " file://pcscd.init"

PACKAGECONFIG = ""

inherit update-rc.d

INITSCRIPT_NAME = "pcscd"
INITSCRIPT_PARAMS = "defaults"

EXTRA_OECONF = " \
    --enable-libusb \
    --enable-usbdropdir=${libdir}/pcsc/drivers \
"

do_install() {
    oe_runmake DESTDIR=${D} install
    install -d ${D}/${sysconfdir}/init.d
    install -m 755 ${UNPACKDIR}/pcscd.init ${D}/${sysconfdir}/init.d/pcscd
}

FILES:${PN} =+ "${sysconfdir}/*"
