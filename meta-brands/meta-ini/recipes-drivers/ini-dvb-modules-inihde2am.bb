SUMMARY = "Hardware drivers for ${MACHINEBUILD}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINEBUILD}"

KV = "3.14.2"
SRCDATE = "20141105"

PV = "${KV}+${SRCDATE}"
PR = "r1"

SRC_URI[md5sum] = "945527288de0e7cca6060f9a37936857"
SRC_URI[sha256sum] = "9f00c22f83708d35016b86ffe20e77b05e86fd4307c9b083692191d5c6884943"

SRC_URI = "http://code-ini.com/software/drivers/ini-422-drivers-${KV}-${SRCDATE}.zip"

S = "${WORKDIR}"

INHIBIT_PACKAGE_STRIP = "1"

inherit module

do_compile() {
}

do_install() {
    install -d ${D}/lib/modules/${KV}/extra
    install -d ${D}/${sysconfdir}/modules-load.d
    for i in dvb; do
        install -m 0755 ${WORKDIR}/$i.ko ${D}/lib/modules/${KV}/extra/dvb_${MACHINEBUILD}.ko
        echo dvb_${MACHINEBUILD} >> ${D}/${sysconfdir}/modules-load.d/_${MACHINEBUILD}.conf
    done
}

FILES_${PN} += "${sysconfdir}/modules-load.d/_${MACHINEBUILD}.conf"
