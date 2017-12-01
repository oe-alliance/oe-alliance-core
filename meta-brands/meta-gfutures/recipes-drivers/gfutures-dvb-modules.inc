SUMMARY = "Hardware drivers for ${MACHINE_DRIVER}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PV = "${KV}+${SRCDATE}"
PR = "r0"

SRC_URI = "http://source.mynonpublic.com/gfutures/${MACHINE_DRIVER}-drivers-${KV}-${SRCDATE}.zip"

S = "${WORKDIR}"

INHIBIT_PACKAGE_STRIP = "1"

do_compile() {
}
do_populate_sysroot() {
}

do_install() {
    install -d ${D}/lib/modules/${KV}/extra
    install -d ${D}/${sysconfdir}/modules-load.d
    for i in ${MACHINE_DRIVER}_1 ${MACHINE_DRIVER}_2 ${MACHINE_DRIVER}_3 ${MACHINE_DRIVER}_4; do
        install -m 0755 ${WORKDIR}/$i.ko ${D}/lib/modules/${KV}/extra
        echo $i >> ${D}/${sysconfdir}/modules-load.d/_${MACHINE_DRIVER}.conf
    done
    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
        install -d ${D}/${sysconfdir}/modprobe.d
        rm ${D}/${sysconfdir}/modprobe.d/_${MACHINE_DRIVER}.conf 2>/dev/null || true
        touch ${D}/${sysconfdir}/modprobe.d/_${MACHINE_DRIVER}.conf
        for i in ${MACHINE_DRIVER}_1 ${MACHINE_DRIVER}_2 ${MACHINE_DRIVER}_3 ${MACHINE_DRIVER}_4; do
            echo blacklist $i >> ${D}/${sysconfdir}/modprobe.d/_${MACHINE_DRIVER}.conf
        done
    fi
}

FILES_${PN} += "${sysconfdir}/modules-load.d/_${MACHINE_DRIVER}.conf ${sysconfdir}/modprobe.d/_${MACHINE_DRIVER}.conf /lib/modules/${KV}/extra"
