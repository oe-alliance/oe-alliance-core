SUMMARY = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PACKAGE_ARCH = "${MACHINEBUILD}"

SRCDATE = "20170106"
KV = "3.14.28"
PV = "${KV}+${SRCDATE}"
PR = "r0"

SRC_URI[md5sum] = "cec94d18d41a48e3512a862c8eec82ba"
SRC_URI[sha256sum] = "a55a975b3c929e2f1334227ec1c1165e0748b1299120087b7d72f49bc5151ad1"

SRC_URI = "http://en3homeftp.net/release/images/oedrivers/bcmlinuxdvb_7252S-200mm-${KV}-${SRCDATE}.tar.gz"

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
