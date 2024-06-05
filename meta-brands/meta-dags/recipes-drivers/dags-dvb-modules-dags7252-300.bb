SUMMARY = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PACKAGE_ARCH = "${MACHINEBUILD}"

SRCDATE = "20210427"
SRCDATE_openspa = "20181001"
KV = "3.14.28"
PV = "${KV}+${SRCDATE}"
PR = "r0"

SRC_URI[md5sum] = "08497ecac0dc87214a5059bfa5ec1767"
SRC_URI[sha256sum] = "fe8fbe9084cc82a517226f99f736ad874269478ce52c13e6988bbd2213a94baf"

SRC_URI = "http://en3homeftp.net/release/images/iqon/bcmlinuxdvb_7252S-300mm-${KV}-${SRCDATE}.tar.gz"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

inherit module

do_compile() {
}

do_populate_sysroot[noexec] = "1"

do_install() {
    install -d ${D}/lib/modules/${KV}/extra
    install -m 0644 ${S}/lib/modules/${KV}/extra/bcmlinuxdvb.ko ${D}/lib/modules/${KV}/extra/bcmlinuxdvb_${MACHINEBUILD}.ko
    install -d ${D}/${sysconfdir}/modules-load.d
    echo bcmlinuxdvb_${MACHINEBUILD} _hwtype=\$hwtypenum >> ${D}/${sysconfdir}/modules-load.d/_${MACHINEBUILD}.conf
}

FILES:${PN} += "${sysconfdir}/modules-load.d/_${MACHINEBUILD}.conf"
