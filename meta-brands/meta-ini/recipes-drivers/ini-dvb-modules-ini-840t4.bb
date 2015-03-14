SUMMARY = "Hardware drivers for ${MACHINEBUILD}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINEBUILD}"

KV = "3.14.2"
SRCDATE = "20150223"

PV = "${KV}+${SRCDATE}"
PR = "r2"

SRC_URI[md5sum] = "d84a27b3cb6314d4781ae69b473626cd"
SRC_URI[sha256sum] = "c1d553d5232fecaf8dc4ad6ee806a2eeb9825d0f9ec7d4d134de76dc7ac954e8"

SRC_URI = "http://code-ini.com/software/drivers/ini-840t4-drivers-${KV}-${SRCDATE}.zip"

RREPLACES_${PN} = "ini-dvb-modules-inihdp"
RCONFLICTS_${PN} = "ini-dvb-modules-inihdp"

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