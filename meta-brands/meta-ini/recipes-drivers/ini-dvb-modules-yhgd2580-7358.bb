SUMMARY = "Hardware drivers for Bwidow X-1"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINEBUILD}"
require conf/license/license-close.inc

KV = "3.12.1"
SRCDATE = "20160122"

PV = "${KV}+${SRCDATE}"
PR = "r1"

SRC_URI[md5sum] = "666809b5d75dd426f0701f24663951ab"
SRC_URI[sha256sum] = "443c472aa9a66f9b7cf76fc260f4a9524e9831a5ea189d745e06b520a14d9ffd"

SRC_URI = "http://source.mynonpublic.com/ini/yhgd2580-7358-drivers-${KV}-${SRCDATE}.zip"

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
