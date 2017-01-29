SUMMARY = "Linux Bluetooth HIDD COMPAT Backport"
SECTION = "console"
LICENSE = "GPLv2+"
require conf/license/license-gplv2.inc

inherit bluetooth

DEPENDS = "glib-2.0 ${BLUEZ}"

PR = "r2"

SRC_URI[md5sum] = "8a7ceca8821017c0cda882b29734122f"
SRC_URI[sha256sum] = "02d3bf69e0de58c2e2182eaf65be9a4c0e753a1a5b303d2943e8043534194d49"

CFLAGS_append = " -Wall -I${S} -I${S}/lib -I${STAGING_INCDIR}/glib-2.0 -I${STAGING_DIR_HOST}/usr/lib/glib-2.0/include"

SRC_URI = "http://source.mynonpublic.com/bluez-hidd-1.1.zip"

S = "${WORKDIR}/bluez-hidd"

inherit pkgconfig

do_install() {
    install -d ${D}/usr/bin
    install -m 755 ${S}/hidd ${D}/usr/bin/
}
