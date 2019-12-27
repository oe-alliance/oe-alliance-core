SUMMARY = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PACKAGE_ARCH = "${MACHINEBUILD}"

SRCDATE = "20191015"
SRCDATE_openspa = "20181001"
KV = "4.2.1"
PV = "${KV}+${SRCDATE}"
PR = "r0"

SRC_URI[md5sum] = "ab994ebea47eb053d03be3d24f26788f"
SRC_URI[sha256sum] = "6254ca6babb232ee3c660237c3e2cdfc72934388b38c15f987f7dd35db4bf2e3"
SRC_URI[openspa.md5sum] = "834f6e6a7ba01edd596cd07db7ca154b"
SRC_URI[openspa.sha256sum] = "8d04837bc5f9b0366c4bd20c6fde9c0abee380d68d33033c01311d05faabde38"

SRC_URI = "http://en3homeftp.net/release/images/oedrivers/bcmlinuxdvb_73625-${KV}-${SRCDATE}.tar.gz"
SRC_URI_openspa = "https://openspa.webhop.info/drivers/qviart/openspa_bcmlinuxdvb_73625-${KV}-${SRCDATE_openspa}.tar.gz;name=openspa"

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
