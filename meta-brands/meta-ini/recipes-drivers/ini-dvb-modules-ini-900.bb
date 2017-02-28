SUMMARY = "Hardware drivers for ${MACHINEBUILD}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINEBUILD}"
require conf/license/license-close.inc

KV = "3.14.2"
SRCDATE = "20160121"

PV = "${KV}+${SRCDATE}"
PR = "r4"

SRC_URI[md5sum] = "0c851b0d74f121144932cae1292b6dbc"
SRC_URI[sha256sum] = "302ca4ed31ff8655115b7d2e8e945cc4e0a212b656413d647524f98f38df6917"

SRC_URI = "http://source.mynonpublic.com/ini/ini-900-drivers-${KV}-${SRCDATE}.zip"

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
