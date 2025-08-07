SUMMARY = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PACKAGE_ARCH = "${MACHINEBUILD}"

SRCDATE = "20200324"
KV = "4.2.1"
PV = "${KV}+${SRCDATE}"
PR = "r0"

SRC_URI[md5sum] = "f5f819843c5a9bec86d463ce947bb9ea"
SRC_URI[sha256sum] = "df455a30190e4e62168af25a510a61ca6622042b43f4d7a27944a3897b25dceb"

SRC_URI = "https://source.mynonpublic.com/dags/bcmlinuxdvb_73625-${KV}-${SRCDATE}.tar.gz"

S = "${UNPACKDIR}"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

do_compile() {
}

do_populate_sysroot() {
}

do_install() {
    install -d ${D}/lib/modules/${KV}/extra
    install -m 0644 ${S}/lib/modules/${KV}/extra/bcmlinuxdvb.ko ${D}/lib/modules/${KV}/extra/bcmlinuxdvb.ko
    install -d ${D}/${sysconfdir}/modules-load.d
    echo bcmlinuxdvb _hwtype=\$hwtypenum >> ${D}/${sysconfdir}/modules-load.d/_${MACHINEBUILD}.conf
}

FILES:${PN} += "${sysconfdir}/modules-load.d/_${MACHINEBUILD}.conf /lib/modules/${KV}/extra"
