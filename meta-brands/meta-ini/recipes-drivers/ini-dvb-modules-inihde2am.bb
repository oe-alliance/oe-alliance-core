SUMMARY = "Hardware drivers for Atemio 6x00"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINEBUILD}"
require conf/license/license-close.inc

KV = "3.14.2"
SRCDATE = "20160122"

PV = "${KV}+${SRCDATE}"
PR = "r1"

SRC_URI[md5sum] = "d337a12a12849d3b96e12d5ad580fea3"
SRC_URI[sha256sum] = "1877059e3c53d7d5c66e0578d0e4efdff4ace03ecd54cec319be09a00e65f99d"

SRC_URI = "http://source.mynonpublic.com/ini/ini-422-drivers-${KV}-${SRCDATE}.zip"

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
