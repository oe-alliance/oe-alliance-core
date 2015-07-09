SUMMARY = "Hardware drivers for ${MACHINEBUILD}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINEBUILD}"
require conf/license/license-close.inc

KV = "3.14.2"
SRCDATE = "20150708"

PV = "${KV}+${SRCDATE}"
PR = "r5"

SRC_URI[md5sum] = "6c525fc1a66275bcf2fcd259fc5fa58a"
SRC_URI[sha256sum] = "d60237ad0ecc3cb67a1874f6b756af0cff50ee91c13a5a6fe71bc6b7a749dc33"

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
