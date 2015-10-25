SUMMARY = "Linux Bluetooth HIDD COMPAT Backport"
SECTION = "console"
DEPENDS = "glib-2.0 bluez5"
LICENSE = "GPLv2+"
require conf/license/license-gplv2.inc

SRC_URI[md5sum] = "f684a38586b9da8350432aed92f5c947"
SRC_URI[sha256sum] = "a4c28cf81488de92adcada26e5f72dfab8566e0b48b395ca093ad4826f67960f"

CFLAGS_append = " -I${S} -I${S}/lib -I=${includedir}/glib-2.0 -I=/usr/lib/glib-2.0/include"

SRC_URI = "http://source.mynonpublic.com/bluez-hidd-1.0.zip"

S = "${WORKDIR}/bluez-hidd"

inherit pkgconfig

do_install() {
    install -d ${D}/usr/bin
    install -m 755 ${S}/hidd ${D}/usr/bin/
}
