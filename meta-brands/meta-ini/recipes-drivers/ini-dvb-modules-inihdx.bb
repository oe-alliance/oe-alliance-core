SUMMARY = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINEBUILD}"
require conf/license/license-close.inc

KV = "3.6.0"
SRCDATE = "20160125"

PV = "${KV}+${SRCDATE}"
PR = "r1"

SRC_URI[md5sum] = "b26afc5dcf01cae27db27bc9f0ab02ce"
SRC_URI[sha256sum] = "5736bec09ef68014c94837f3ebabb96d2d0a492d58e3d6e58c747610a5084411"

SRC_URI = "http://source.mynonpublic.com/ini/ini-x000-drivers-${KV}-${SRCDATE}.zip"

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
        install -m 0755 ${WORKDIR}/$i.ko ${D}/lib/modules/${KV}/extra
        echo $i >> ${D}/${sysconfdir}/modules-load.d/_${MACHINE}.conf
    done
}

FILES_${PN} += "${sysconfdir}/modules-load.d/_${MACHINE}.conf /lib/modules/${KV}/extra"
