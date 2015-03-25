SSUMMARY = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PV = "${KV}+${SRCDATE}"
PR = "r0"

SRC_URI = "http://source.mynonpublic.com/ceryon/ceryon-${MACHINE}-dvbdrive-${SRCDATE}.zip"

S = "${WORKDIR}"

INHIBIT_PACKAGE_STRIP = "1"

inherit module

do_compile() {
}

do_install() {
    install -d ${D}/lib/modules/${KV}/extra
    install -d ${D}/${sysconfdir}/modules-load.d
    echo "dvbdrive" >> ${D}/${sysconfdir}/modules-load.d/dvbdrive.conf
    install -m 0755 ${WORKDIR}/dvbdrive.ko ${D}/lib/modules/${KV}/extra
    install -d ${D}/etc/rcS.d
}

FILES_${PN} += "${sysconfdir}/modules-load.d/dvbdrive.conf"
