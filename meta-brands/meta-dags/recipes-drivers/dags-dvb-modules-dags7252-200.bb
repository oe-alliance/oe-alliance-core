SUMMARY = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PACKAGE_ARCH = "${MACHINEBUILD}"

SRCDATE = "20170517"
KV = "3.14.28"
PV = "${KV}+${SRCDATE}"
PR = "r0"

SRC_URI[md5sum] = "bfbcbb362bb22ff446d332a0f0072160"
SRC_URI[sha256sum] = "0f6b8c73e9aef5b7ffc80c8a19cb45d7a25f4b90fc29dc3dca95b6dafc8887c8"

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
