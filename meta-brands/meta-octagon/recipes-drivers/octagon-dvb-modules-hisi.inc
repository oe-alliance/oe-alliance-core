SUMMARY = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc
PACKAGE_ARCH = "${MACHINE_ARCH}"

PV = "${KV}+${SRCDATE}"
PR = "r1"

SRC_URI = "https://source.mynonpublic.com/octagon/${MACHINE}-hiko-${SRCDATE}.zip \
    file://modules \
"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

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
    install -m 0644 ${UNPACKDIR}/hiko/*.ko ${D}/lib/modules/${KV}/extra
    install -m 0755 ${UNPACKDIR}/hiko/load ${D}/lib/modules/${KV}/extra
    install -d ${D}/${sysconfdir}/modules-load.d
    install -m 0644 ${UNPACKDIR}/modules ${D}${sysconfdir}/modules-load.d/_${MACHINE}.conf
}
