FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

DEPENDS += "libusb1"
RDEPENDS:${PN} += "libusb1"

SRC_URI:append = " file://pcscd.init"

PACKAGECONFIG = ""

inherit update-rc.d

INITSCRIPT_NAME = "pcscd"
INITSCRIPT_PARAMS = "defaults"

EXTRA_OEMESON:remove = "-Dlibusb=false"
EXTRA_OEMESON = " \
    -Dlibusb=true \
    -Dusbdropdir=${libdir}/pcsc/drivers \
"

do_install:append() {
    install -d ${D}/${sysconfdir}/init.d
    install -m 755 ${UNPACKDIR}/pcscd.init ${D}/${sysconfdir}/init.d/pcscd
}

FILES:${PN} =+ "${sysconfdir}/*"
