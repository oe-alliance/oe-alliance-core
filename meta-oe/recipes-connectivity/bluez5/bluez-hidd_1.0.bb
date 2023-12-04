SUMMARY = "Linux Bluetooth HIDD COMPAT Backport"
SECTION = "console/network"
LICENSE = "GPL-2.0-or-later"
require conf/license/license-gplv2.inc

DEPENDS = "glib-2.0 bluez5"

SRC_URI[md5sum] = "9b8203723ead15813ca731f10ba640aa"
SRC_URI[sha256sum] = "1889fe89b1ec8e725d3404f3d8125d5e824a4cc0ab05891ef1b755a61a1cea46"

CFLAGS:append = " -Wall -I${S} -I${S}/lib -I${STAGING_INCDIR}/glib-2.0 -I${STAGING_DIR_HOST}${libdir}/glib-2.0/include"

SRC_URI = "https://source.mynonpublic.com/bluez-hidd-1.4.zip"

S = "${WORKDIR}/bluez-hidd"

inherit pkgconfig

do_install() {
    install -d ${D}/usr/bin
    install -m 755 ${S}/hidd ${D}/usr/bin/
}

INSANE_SKIP:${PN} += "ldflags"
