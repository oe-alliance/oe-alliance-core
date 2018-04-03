SUMMARY = "Hardware drivers for Beyonwiz T2"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINEBUILD}"

KV = "3.14.2"
SRCDATE = "20180324"

PV = "${KV}+${SRCDATE}"

SRC_URI[md5sum] = "9d99aeed41f43aafa027d5769be26536"
SRC_URI[sha256sum] = "7e488fc0f99d8e458d7bb07d84df56fec50023c5bafee0b1a394c42044b9ed5b"

SRC_URI = "http://source.mynonpublic.com/ini/ini-442dt-drivers-${KV}-${SRCDATE}.zip"

S = "${WORKDIR}"

INHIBIT_PACKAGE_STRIP = "1"

do_compile() {
}
do_populate_sysroot() {
}

do_install() {
    install -d ${D}/lib/modules/${KV}/extra
    install -d ${D}/${sysconfdir}/modules-load.d
    for i in dvb; do
        install -m 0755 ${WORKDIR}/$i.ko ${D}/lib/modules/${KV}/extra/$i.ko
        echo $i >> ${D}/${sysconfdir}/modules-load.d/_${MACHINEBUILD}.conf
    done
}

FILES_${PN} += "${sysconfdir}/modules-load.d/_${MACHINEBUILD}.conf /lib/modules/${KV}/extra"
