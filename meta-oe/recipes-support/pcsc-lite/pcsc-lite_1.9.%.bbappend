FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

DEPENDS += "libusb1 ${PYTHON_PN}"
RDEPENDS:${PN} += "libusb1 ${PYTHON_PN}"

SRC_URI:append = " file://pcscd.init"

RDEPENDS:${PN}-spy += "${PYTHON_PN}"

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
    install -m 755 ${WORKDIR}/pcscd.init ${D}/${sysconfdir}/init.d/pcscd
}

FILES:${PN} =+ "${sysconfdir}/*"
