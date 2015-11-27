SUMMARY = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PACKAGE_ARCH = "${MACHINEBUILD}"

SRCDATE = "20151126"
KV = "3.9.7"
PV = "${KV}+${SRCDATE}"
PR = "r3"

SRC_URI[md5sum] = "4a6dd84f5ffdad3450ff59b36b98eb6f"
SRC_URI[sha256sum] = "1acea0e430f77970b22ec2d77c61ebd4de82c4ddf9a13b9fcbc5228a6487b7d1"

SRC_URI = "http://en3homeftp.net/release/images/oedrivers/bcmlinuxdvb_7356-${KV}-${SRCDATE}.tar.gz"

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
