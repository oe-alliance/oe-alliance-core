SUMMARY = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PACKAGE_ARCH = "${MACHINEBUILD}"

SRCDATE = "20160418"
KV = "3.9.7"
PV = "${KV}+${SRCDATE}"
PR = "r1"

SRC_URI = "http://en3homeftp.net/release/images/oedrivers/bcmlinuxdvb_7335-${KV}-${SRCDATE}.tar.gz"

SRC_URI[md5sum] = "e5cbaf0ef0941a90f0cbd10ee48604ba"
SRC_URI[sha256sum] = "02d6f5897ba2b93f53cb1dfea8822248f5da9e5f2466ef2597cf780611c94022"

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
