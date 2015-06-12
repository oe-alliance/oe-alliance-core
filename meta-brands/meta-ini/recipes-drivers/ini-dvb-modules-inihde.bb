SUMMARY = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINEBUILD}"
require conf/license/license-close.inc

KV = "3.12.1"
SRCDATE = "20150612"

PV = "${KV}+${SRCDATE}"
PR = "r1"

SRC_URI[md5sum] = "46d7529bbb41a9323ef4ac3c44609577"
SRC_URI[sha256sum] = "a55d64c28e566386c7e599dc8c36c3b04b5f35030878549907f13760ad7f4023"

SRC_URI = "http://code-ini.com/software/drivers/ini-1000-drivers-${KV}-${SRCDATE}.zip"

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
