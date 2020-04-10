SUMMARY = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PACKAGE_ARCH = "${MACHINEBUILD}"

SRCDATE = "20200409"
SRCDATE_openspa = "20181001"
KV = "3.14.28"
PV = "${KV}+${SRCDATE}"
PR = "r0"

SRC_URI[md5sum] = "0ab1f767f63faf5d42ffeb9511d98ae1"
SRC_URI[sha256sum] = "b6c295095598a89a58d89e3269e6984494052f3521cc83ba64ba515a4e446cf6"
SRC_URI[openspa.md5sum] = "866c3b336005157ddd17249226ebe701"
SRC_URI[openspa.sha256sum] = "25f1818ea20faae5dafa787a46a297fd471b0cd0231e3b718bc267b4390981b1"

SRC_URI = "http://en3homeftp.net/release/images/oedrivers/bcmlinuxdvb_7252S-300mm-${KV}-${SRCDATE}.tar.gz"
SRC_URI_openspa = "https://openspa.webhop.info/drivers/qviart/openspa_bcmlinuxdvb_7252S-300mm-${KV}-${SRCDATE_openspa}.tar.gz;name=openspa"

S = "${WORKDIR}"

INHIBIT_PACKAGE_STRIP = "1"

inherit module

do_compile() {
}

do_populate_sysroot[noexec] = "1"

do_install() {
    install -d ${D}/lib/modules/${KV}/extra
    install -m 0644 ${WORKDIR}/lib/modules/${KV}/extra/bcmlinuxdvb.ko ${D}/lib/modules/${KV}/extra/bcmlinuxdvb_${MACHINEBUILD}.ko
    install -d ${D}/${sysconfdir}/modules-load.d
    echo bcmlinuxdvb_${MACHINEBUILD} _hwtype=\$hwtypenum >> ${D}/${sysconfdir}/modules-load.d/_${MACHINEBUILD}.conf
}

FILES_${PN} += "${sysconfdir}/modules-load.d/_${MACHINEBUILD}.conf"
