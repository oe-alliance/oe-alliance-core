SUMMARY = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINEBUILD}"
require conf/license/license-close.inc

KV = "3.14.2"
SRCDATE = "20160122"

PV = "${KV}+${SRCDATE}"
PR = "r1"

SRC_URI[md5sum] = "a0bbd2b0a26d750e89972364e52fa4e9"
SRC_URI[sha256sum] = "80997180ab4092afbbefe1e42914ff1c66fe9de861789e9c4b86027dbddb840e"

SRC_URI = "http://source.mynonpublic.com/ini/yhgd5034-drivers-${KV}-${SRCDATE}.zip"

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
