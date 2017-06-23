SUMMARY = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PACKAGE_ARCH = "${MACHINEBUILD}"

SRCDATE = "20170622"
KV = "4.2.1"
PV = "${KV}+${SRCDATE}"
PR = "r0"

SRC_URI[md5sum] = "d20b890f835a1edd0b198e106252b61c"
SRC_URI[sha256sum] = "7326cfe1e559c8d376efa38530c5257386d164d3c70b5aa0b53e721511276ee9"

SRC_URI = "http://en3homeftp.net/release/images/oedrivers/bcmlinuxdvb_73625-${KV}-${SRCDATE}.tar.gz"

S = "${WORKDIR}"

INHIBIT_PACKAGE_STRIP = "1"

do_compile() {
}

do_populate_sysroot() {
}

do_install() {
    install -d ${D}/lib/modules/${KV}/extra
    install -m 0644 ${WORKDIR}/lib/modules/${KV}/extra/bcmlinuxdvb.ko ${D}/lib/modules/${KV}/extra/bcmlinuxdvb.ko
    install -d ${D}/${sysconfdir}/modules-load.d
    echo bcmlinuxdvb _hwtype=\$hwtypenum >> ${D}/${sysconfdir}/modules-load.d/_${MACHINEBUILD}.conf
}

FILES_${PN} += "${sysconfdir}/modules-load.d/_${MACHINEBUILD}.conf /lib/modules/${KV}/extra"
