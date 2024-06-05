SUMMARY = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc
PACKAGE_ARCH = "${MACHINEBUILD}"

RDEPENDS:${PN} += "firmware-mn8847x"

PV = "${KV}+${SRCDATE}"
PR = "r2"

SRC_URI = "https://source.mynonpublic.com/xcore/${MACHINE_DRIVER}-drivers-${KV}-${SRCDATE}.zip"

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
    for i in ${KOFILES} ; do
        install -m 0755 ${S}/${i}.ko ${D}/lib/modules/${KV}/extra/${i}.ko
        echo ${i} >> ${D}/${sysconfdir}/modules-load.d/_${MACHINEBUILD}.conf
    done
}

FILES:${PN} += "${sysconfdir}/modules-load.d/_${MACHINEBUILD}.conf /lib/modules/${KV}/extra"
