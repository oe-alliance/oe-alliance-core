SUMMARY = "Hardware drivers for ${MACHINEBUILD}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINEBUILD}"

KV = "3.14.2"
SRCDATE = "20150407"

PV = "${KV}+${SRCDATE}"
PR = "r1"

SRC_URI[md5sum] = "2303722a6a3531f5e68396144ad802af"
SRC_URI[sha256sum] = "00c02ddb748d5dc2b944a4d5f47572141f9019eadf8df8188c5d741601f6811a"

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
