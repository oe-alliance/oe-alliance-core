SUMMARY = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

PV = "${KV}+${SRCDATE}"
PR = "r3"

DRIVERMACHINE ?= "${MACHINE}"

SRC_URI = "http://www.flashimage.net/drivers/cube-drivers-${KV}-${SRCDATE}.zip"

S = "${WORKDIR}"

INHIBIT_PACKAGE_STRIP = "1"

inherit module

do_compile() {
}

do_install() {
    install -d ${D}/lib/modules/${KV}/extra
    install -d ${D}/${sysconfdir}/modules-load.d
    install -m 0755 ${WORKDIR}/*.ko ${D}/lib/modules/${KV}/extra
    if [ -d ${WORKDIR}/firmware ]; then
        install -d ${D}/lib/firmware
        install -m 0644 ${WORKDIR}/firmware/* ${D}/lib/firmware
    fi
    for i in ${DRIVERMACHINE}_1 ${DRIVERMACHINE}_2 ${DRIVERMACHINE}_3 ${DRIVERMACHINE}_4; do
        echo $i >> ${D}/${sysconfdir}/modules-load.d/_${DRIVERMACHINE}.conf
    done
}

FILES_${PN} += "/lib/firmware ${sysconfdir}/modules-load.d/_${DRIVERMACHINE}.conf"
