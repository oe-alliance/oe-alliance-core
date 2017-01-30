SUMMARY = "Linux Bluetooth HIDD COMPAT Backport"
SECTION = "console"
LICENSE = "GPLv2+"
require conf/license/license-gplv2.inc

inherit bluetooth

DEPENDS = "glib-2.0 ${BLUEZ}"

PR = "r3"

SRC_URI[md5sum] = "67b5cc0361336faa85c25df92edb2e2a"
SRC_URI[sha256sum] = "b63236cd9b21c9224af11ab03aabfa1c9cff69b59540be3a755c40d1b91f5d2f"

CFLAGS_append = " -Wall -I${S} -I${S}/lib -I${STAGING_INCDIR}/glib-2.0 -I${STAGING_DIR_HOST}/usr/lib/glib-2.0/include"

SRC_URI = "http://source.mynonpublic.com/bluez-hidd-1.2.zip"

S = "${WORKDIR}/bluez-hidd"

inherit pkgconfig

do_install() {
    install -d ${D}/usr/bin
    install -m 755 ${S}/hidd ${D}/usr/bin/
}
