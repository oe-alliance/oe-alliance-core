SUMMARY = "Hardware drivers for Miraclebox MINI, Sezam 1000HD, Xpeed LX12"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINEBUILD}"
require conf/license/license-close.inc

KV = "3.12.1"
SRCDATE = "20160122"

PV = "${KV}+${SRCDATE}"
PR = "r1"

SRC_URI[md5sum] = "efc03d2687c7204a2475e8b727ce67eb"
SRC_URI[sha256sum] = "f2acb694b38f6ab90c79cb7ef4650253c2b204ad611cdf9ad22469a7850e3af7"

SRC_URI = "https://source.mynonpublic.com/ini/ini-1000-drivers-${KV}-${SRCDATE}.zip"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

do_compile() {
}
do_populate_sysroot() {
}

do_install() {
    install -d ${D}/lib/modules/${KV}/extra
    install -d ${D}/${sysconfdir}/modules-load.d
    for i in dvb; do
        install -m 0755 ${S}/$i.ko ${D}/lib/modules/${KV}/extra
        echo $i >> ${D}/${sysconfdir}/modules-load.d/_${MACHINE}.conf
    done
}

FILES:${PN} += "${sysconfdir}/modules-load.d/_${MACHINE}.conf /lib/modules/${KV}/extra"
