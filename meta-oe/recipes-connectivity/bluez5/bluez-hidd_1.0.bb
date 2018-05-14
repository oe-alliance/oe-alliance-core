SUMMARY = "Linux Bluetooth HIDD COMPAT Backport"
SECTION = "console"
LICENSE = "GPLv2+"
require conf/license/license-gplv2.inc

inherit bluetooth

DEPENDS = "glib-2.0 ${BLUEZ}"

PR = "r5"

SRC_URI[md5sum] = "9b8203723ead15813ca731f10ba640aa"
SRC_URI[sha256sum] = "1889fe89b1ec8e725d3404f3d8125d5e824a4cc0ab05891ef1b755a61a1cea46"

CFLAGS_append = " -Wall -I${S} -I${S}/lib -I${STAGING_INCDIR}/glib-2.0 -I${STAGING_DIR_HOST}${libdir}/glib-2.0/include"

SRC_URI = "http://source.mynonpublic.com/bluez-hidd-1.4.zip"

S = "${WORKDIR}/bluez-hidd"

inherit pkgconfig

do_install() {
    install -d ${D}/usr/bin
    install -m 755 ${S}/hidd ${D}/usr/bin/
}
