SUMMARY = "Hardware drivers for ${MACHINEBUILD}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINEBUILD}"

KV = "3.14.2"
SRCDATE = "20150113"

PV = "${KV}+${SRCDATE}"
PR = "r2"

SRC_URI[md5sum] = "5e6f395c4ef6eebaf19c6a7f14cfcb7f"
SRC_URI[sha256sum] = "f635367e7ca532b2aca04ab181257400c6e5baac9c56fe523ebb9caae6d06f66"

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