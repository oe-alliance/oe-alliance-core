SUMMARY = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PACKAGE_ARCH = "${MACHINEBUILD}"

SRCDATE = "20170714"
KV = "4.2.1"
PV = "${KV}+${SRCDATE}"
PR = "r2"

SRC_URI[md5sum] = "f13ca563fe1a6a3481c9e70c8c054a6f"
SRC_URI[sha256sum] = "cc8099309c91f931e52bb208d3fd4352a9dcf86b1b3d9bf98fed3700c93d4068"

SRC_URI = "http://en3homeftp.net/release/images/oedrivers/bcmlinuxdvb_7362-${KV}-${SRCDATE}.tar.gz"

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
