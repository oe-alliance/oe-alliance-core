SUMMARY = "Hardware drivers for ${MACHINEBUILD}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINEBUILD}"
require conf/license/license-close.inc

KV = "3.14.2"
SRCDATE = "20160121"

PV = "${KV}+${SRCDATE}"

SRC_URI[md5sum] = "292d96aa1a11455a2d77963adab9065e"
SRC_URI[sha256sum] = "cdbacedfbc49c8854abde93793703baec81169f337ad7bd671874dad4e7317c3"

SRC_URI = "http://source.mynonpublic.com/ini/ini-840t4-drivers-${KV}-${SRCDATE}.zip"

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