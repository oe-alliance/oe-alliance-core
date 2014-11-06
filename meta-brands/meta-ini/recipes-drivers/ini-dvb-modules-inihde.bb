SUMMARY = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINEBUILD}"

KV = "3.12.1"
SRCDATE = "20141105"

PV = "${KV}+${SRCDATE}"
PR = "r1"

SRC_URI[md5sum] = "914bbf931b691c6b61aeb6b07a7461da"
SRC_URI[sha256sum] = "ca507b04ab06145ddfd46d8f5e6f176f7a0cf6e26e66238803bae7286c82d20b"

SRC_URI = "http://code-ini.com/software/drivers/ini-1000-drivers-${KV}-${SRCDATE}.zip"

S = "${WORKDIR}"

INHIBIT_PACKAGE_STRIP = "1"

inherit module

do_compile() {
}

do_install() {
    install -d ${D}/lib/modules/${KV}/extra
    install -d ${D}/${sysconfdir}/modules-load.d
    for i in dvb; do
        install -m 0755 ${WORKDIR}/$i.ko ${D}/lib/modules/${KV}/extra
        echo $i >> ${D}/${sysconfdir}/modules-load.d/_${MACHINE}.conf
    done
}

FILES_${PN} += "${sysconfdir}/modules-load.d/_${MACHINE}.conf"
