SUMMARY = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc
PACKAGE_ARCH = "${MACHINE}"

PV = "${KV}+${SRCDATE}"
PR = "r2"

SRC_URI = "https://source.mynonpublic.com/octagon/${MACHINE}-drivers-${KV}-${GCCREV}-${SRCDATE}.zip"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

do_compile() {
}
do_populate_sysroot() {
}

do_install() {
    install -d ${D}/lib/modules/${KV}/extra
    install -d ${D}/${sysconfdir}/modules-load.d
    install -m 0755 ${S}/linuxtv.ko ${D}/lib/modules/${KV}/extra/linuxtv-kodi.ko
    touch ${D}/${sysconfdir}/${MACHINE}-kodi
}

pkg_postinst:${PN}() {
#!/bin/sh
if [ -e /lib/modules/${KV}/extra/linuxtv-kodi.ko ]; then
 mv -f /lib/modules/${KV}/extra/linuxtv-kodi.ko /lib/modules/${KV}/extra/linuxtv.ko
fi
exit 0
}

FILES:${PN} += "${sysconfdir}/${MACHINE}-kodi /lib/modules/${KV}/extra"
