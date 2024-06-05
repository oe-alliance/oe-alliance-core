SUMMARY = "Hardware drivers for ${MACHINE_DRIVER}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PV = "${KV}+${SRCDATE}"
PR = "r0"

SRC_URI = "https://source.mynonpublic.com/ceryon/${MACHINE_DRIVER}-drivers-${KV}-${SRCDATE}.zip"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

do_compile() {
}
do_populate_sysroot() {
}

do_install() {
    install -d ${D}/lib/modules/${KV}/extra
    install -d ${D}/${sysconfdir}/modules-load.d
    for i in ${MACHINE_DRIVER}_1 ${MACHINE_DRIVER}_2 ${MACHINE_DRIVER}_3 ${MACHINE_DRIVER}_4; do
        install -m 0755 ${S}/$i.ko ${D}/lib/modules/${KV}/extra
        echo $i >> ${D}/${sysconfdir}/modules-load.d/_${MACHINE_DRIVER}.conf
    done
}

FILES:${PN} += "${sysconfdir}/modules-load.d/_${MACHINE_DRIVER}.conf /lib/modules/${KV}/extra"
