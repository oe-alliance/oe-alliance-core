SUMMARY = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc

SRCDATE = "20140918"
KV = "3.12.4"
PV = "${KV}+${SRCDATE}"
PR = "r1"

SRC_URI[md5sum] = "a365590ff69165742362a98cc1c008b7"
SRC_URI[sha256sum] = "89a97285f1760c54e41903b85749aad38a277eeb2ffada27a70f78a0b0d0de89"

SRC_URI = "http://archiv.mixos-support.com/ebox-dvb-modules-${MACHINE}-${KV}-${SRCDATE}.zip"


S = "${WORKDIR}"

INHIBIT_PACKAGE_STRIP = "1"

inherit module

do_compile() {
}

do_install() {
    install -d ${D}/lib/modules/${KV}/extra
    for f in *.ko; do
        install -m 0644 ${WORKDIR}/$f ${D}/lib/modules/${KV}/extra/$f;
    done
    install -d ${D}/${sysconfdir}/modules-load.d
    for i in `ls | grep \\.ko | sed -e 's/.ko//g'`; do
        echo $i >> ${D}/${sysconfdir}/modules-load.d/_${MACHINE}.conf
    done
}

FILES_${PN} += "${sysconfdir}/modules-load.d/_${MACHINE}.conf"