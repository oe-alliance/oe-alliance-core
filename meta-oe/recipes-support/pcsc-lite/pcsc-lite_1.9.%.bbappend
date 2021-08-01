FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

DEPENDS += "libusb1 ${PYTHON_PN}"
RDEPENDS:${PN} += "libusb1 ${PYTHON_PN}"

SRC_URI:append = " file://pcscd.init"

# if PYTHON_PN is python2
SRC_URI:remove = "${@bb.utils.contains("PYTHON_PN", "python", "0001-pcsc-spy-use-python3-only.patch", "", d)}"
RDEPENDS:${PN}-spy:remove = "${@bb.utils.contains("PYTHON_PN", "python", "python3", "", d)}"
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
