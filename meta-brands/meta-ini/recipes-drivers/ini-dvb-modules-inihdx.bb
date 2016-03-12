SUMMARY = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINEBUILD}"
require conf/license/license-close.inc

KV = "3.6.0"
SRCDATE = "20160125"

PV = "${KV}+${SRCDATE}"
PR = "r1"

SRC_URI[md5sum] = "d072cd602211a6b4a1eda968df36cdc1"
SRC_URI[sha256sum] = "083d15a07f8702e1216f5ec39ee1879d1459e307a6ee7ae223651fed856dae93"

SRC_URI = "http://code-ini.com/software/drivers/ini-x000-drivers-${KV}-${SRCDATE}.zip"

S = "${WORKDIR}"

INHIBIT_PACKAGE_STRIP = "1"

inherit module

do_compile() {
}

do_install() {
    install -d ${D}/lib/modules/${KV}/extra
    install -d ${D}/${sysconfdir}/modules-load.d
    for i in dvb; do
        install -m 0755 ${WORKDIR}/$i.ko ${D}/lib/modules/${KV}/extra
        echo $i >> ${D}/${sysconfdir}/modules-load.d/_${MACHINE}.conf
    done
}

FILES_${PN} += "${sysconfdir}/modules-load.d/_${MACHINE}.conf"
