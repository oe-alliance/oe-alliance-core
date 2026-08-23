SUMMARY = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
require conf/license/license-close.inc
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit hisi-hifb-wqhd hisi-keys-power-only

PV = "${KV}+${SRCDATE}"
PR = "r2"

SRC_URI = "https://source.mynonpublic.com/beyonwiz/${MACHINE}-hiko-${SRCDATE}.zip \
    file://modules \
"

S = "${UNPACKDIR}"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

FILES:${PN} += "${sysconfdir}/modules-load.d/_${MACHINE}.conf"
FILES:${PN} += "/lib/modules/${KV}/extra"

do_compile() {
}
do_populate_sysroot() {
}

do_install() {
    install -d ${D}/lib/modules/${KV}/extra
    install -m 0644 ${S}/hiko/*.ko ${D}/lib/modules/${KV}/extra
    install -m 0755 ${S}/hiko/load ${D}/lib/modules/${KV}/extra
    hisi_hifb_wqhd_patch "${D}/lib/modules/${KV}/extra/hi_fb.ko" "${D}/lib/modules/${KV}/extra/load"
    hisi_keys_power_only_patch "${D}/lib/modules/${KV}/extra/hisi_keys.ko"
    install -d ${D}/${sysconfdir}/modules-load.d
    install -m 0644 ${S}/modules ${D}${sysconfdir}/modules-load.d/_${MACHINE}.conf
}
