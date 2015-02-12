SUMMARY = "Hardware drivers for ${MACHINEBUILD}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINEBUILD}"

KV = "3.14.2"
SRCDATE = "20150211"

PV = "${KV}+${SRCDATE}"
PR = "r2"

SRC_URI[md5sum] = "10167b3accedcc9fee4aabd33d6b75f8"
SRC_URI[sha256sum] = "e0d2d1ae95a77b1a277dc874f6812d9d14456718a6a8989bfeaa4b56e9ca5fcd"

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