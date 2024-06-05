SUMMARY = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc
PACKAGE_ARCH = "${MACHINE}"

PV = "${KV}+${SRCDATE}"
PR = "r2"

SRC_URI = "https://source.mynonpublic.com/xtrend/${MACHINE_DRIVER}-drivers-kodi-${KV}-${GCCREV}-${SRCDATE}.zip"

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
    install -m 0755 ${S}/tpm.ko ${D}/lib/modules/${KV}/extra/tpm-kodi.ko
    install -m 0755 ${S}/modloader.ko ${D}/lib/modules/${KV}/extra/modloader-kodi.ko
    install -m 0755 ${S}/modloader2.ko ${D}/lib/modules/${KV}/extra/modloader2-kodi.ko
    install -m 0755 ${S}/dvb.ko ${D}/lib/modules/${KV}/extra/dvb-kodi.ko
    touch ${D}/${sysconfdir}/${MACHINE}-kodi
}

pkg_postinst:${PN}() {
#!/bin/sh
if [ -e /lib/modules/${KV}/extra/tpm-kodi.ko ]; then
 mv -f /lib/modules/${KV}/extra/tpm-kodi.ko /lib/modules/${KV}/extra/tpm.ko
 mv -f /lib/modules/${KV}/extra/modloader-kodi.ko /lib/modules/${KV}/extra/modloader.ko
 mv -f /lib/modules/${KV}/extra/modloader2-kodi.ko /lib/modules/${KV}/extra/modloader2.ko
 mv -f /lib/modules/${KV}/extra/dvb-kodi.ko /lib/modules/${KV}/extra/dvb.ko
fi
exit 0
}

FILES:${PN} += "${sysconfdir}/${MACHINE}-kodi /lib/modules/${KV}/extra"
