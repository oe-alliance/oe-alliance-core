SUMMARY = "Hardware drivers for ${MACHINEBUILD}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINEBUILD}"

KV = "3.14.2"
SRCDATE = "20150130"

PV = "${KV}+${SRCDATE}"
PR = "r5"

SRC_URI[md5sum] = "d960d27e49a4299d330534b98bf6e8d8"
SRC_URI[sha256sum] = "52a965c7d9f803c76616f164d9367853d1bfbcb40e9436b9247c92b0fa48c002"

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