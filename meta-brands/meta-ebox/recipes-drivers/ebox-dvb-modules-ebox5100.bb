SUMMARY = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

SRCDATE = "20140120"
KV = "3.12.4"
PV = "${KV}+${SRCDATE}"
PR = "r1"

SRC_URI[md5sum] = "c2260177b40eefcc0824a5af84c37e16"
SRC_URI[sha256sum] = "34d3a21de0534d4078d790add5676c519fb53765aace53faee055b2a9627e24b"

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