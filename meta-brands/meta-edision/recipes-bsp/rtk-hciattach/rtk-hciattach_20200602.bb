SUMMARY = "Realtek Bluetooth UART and USB driver"
HOMEPAGE = "https://www.realtek.com/"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://Makefile;md5=ac320a4e1d358e1234ea8489eca682e8"

SRC_URI = "git://github.com/edision-open/LINUX_BT_DRIVER_RTL8723D.git"

SRCREV = "6a68bd988096b6fc801cda9a2fed1e86164d0c1c"

S = "${WORKDIR}/git/uart/rtk_hciattach"

EXTRA_OEMAKE = 'CC="${CC}"'

do_install() {
    install -d ${D}${bindir}
    install -m 755 ${S}/rtk_hciattach ${D}${bindir}
}
