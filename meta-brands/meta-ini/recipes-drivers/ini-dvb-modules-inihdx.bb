SUMMARY = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINEBUILD}"
require conf/license/license-close.inc

KV = "3.6.0"
SRCDATE = "20160406"

PV = "${KV}+${SRCDATE}"
PR = "r1"

SRC_URI[md5sum] = "35c14df7d4fefdae315fb9bc64a8b006"
SRC_URI[sha256sum] = "df587ae3e817cb07f4303c4bac9b0ad58a25cd362f2392ddb19776e629e3703e"

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
