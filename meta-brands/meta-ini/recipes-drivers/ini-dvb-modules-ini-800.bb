SUMMARY = "Hardware drivers for ${MACHINEBUILD}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINEBUILD}"
require conf/license/license-close.inc

KV = "3.14.2"
SRCDATE = "20150518"

PV = "${KV}+${SRCDATE}"
PR = "r5"

SRC_URI[md5sum] = "59e9119dde4b933e216a8d580c69fe57"
SRC_URI[sha256sum] = "de8199cf2678c77e29f1e18fa71d43c3f86f0dbbd2021804a18ddfb8077436e9"

SRC_URI = "http://code-ini.com/software/drivers/ini-800-drivers-${KV}-${SRCDATE}.zip"

S = "${WORKDIR}"

INHIBIT_PACKAGE_STRIP = "1"

inherit module

do_compile() {
}

do_install() {
    install -d ${D}/lib/modules/${KV}/extra
    install -d ${D}/${sysconfdir}/modules-load.d
    for i in dvb; do
        install -m 0755 ${WORKDIR}/$i.ko ${D}/lib/modules/${KV}/extra/dvb_${MACHINEBUILD}.ko
        echo dvb_${MACHINEBUILD} >> ${D}/${sysconfdir}/modules-load.d/_${MACHINEBUILD}.conf
    done
    
    install -d ${D}/${sysconfdir}/modprobe.d
    echo "blacklist rtk_btusb" > ${D}/${sysconfdir}/modprobe.d/blacklist.conf    
}

FILES_${PN} += "${sysconfdir}/modules-load.d/_${MACHINEBUILD}.conf"