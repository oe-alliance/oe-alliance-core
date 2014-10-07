SUMMARY = "Hardware drivers for ${MACHINEBUILD}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINEBUILD}"

KV = "3.14.2"
SRCDATE = "20140904"

PV = "${KV}+${SRCDATE}"
PR = "r2"

SRC_URI[md5sum] = "f00302ddeed036f463f343e84384eeeb"
SRC_URI[sha256sum] = "62e52fe3f70893845d4fb0ff3f6c6e21110c4267c773be0298a8ade917b8fa1b"

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