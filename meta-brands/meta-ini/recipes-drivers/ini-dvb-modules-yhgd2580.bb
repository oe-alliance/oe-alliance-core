SUMMARY = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINEBUILD}"

KV = "3.12.1"
SRCDATE = "20150211"

PV = "${KV}+${SRCDATE}"
PR = "r1"

SRC_URI[md5sum] = "0a1b4be76a109a3f743333433e030c0d"
SRC_URI[sha256sum] = "5c78de6e8f245f91279610a7d22761cedb7e08a80cadb48cfa056094cf29aa65"

SRC_URI = "http://code-ini.com/software/drivers/yhgd2580-drivers-${KV}-${SRCDATE}.zip"

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
}

FILES_${PN} += "${sysconfdir}/modules-load.d/_${MACHINEBUILD}.conf"
