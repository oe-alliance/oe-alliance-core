SUMMARY = "Realtek Bluetooth UART and USB driver"
HOMEPAGE = "https://www.realtek.com/"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${UNPACKDIR}/${BB_GIT_DEFAULT_DESTSUFFIX}/Makefile;md5=0720b2634084c3a0d91fade1c57b31df"

SRC_URI = "git://github.com/edision-open/LINUX_BT_DRIVER_RTL8723D.git;branch=master;protocol=https"

SRCREV = "6a68bd988096b6fc801cda9a2fed1e86164d0c1c"

S = "${UNPACKDIR}/${BB_GIT_DEFAULT_DESTSUFFIX}/uart/rtk_hciattach"

EXTRA_OEMAKE = 'CC="${CC}"'

do_install() {
    install -d ${D}${bindir}
    install -m 755 ${B}/rtk_hciattach ${D}${bindir}
}
