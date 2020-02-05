FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

DEPENDS += "libusb1 python"
RDEPENDS_${PN} += "libusb1 python"

SRC_URI_append = " file://pcscd.init"

# we are by python2 still
SRC_URI_remove = "0001-pcsc-spy-use-python3-only.patch"
RDEPENDS_${PN}-spy_remove ="python3"
RDEPENDS_${PN}-spy +="python"

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

FILES_${PN} =+ "${sysconfdir}/*"
