SUMMARY = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc

SRCDATE = "20150422"
KV = "3.12.4"
PV = "${KV}+${SRCDATE}"
PR = "r1"

SRC_URI[md5sum] = "9f2e9b2a541424c022ee1ffb18021be5"
SRC_URI[sha256sum] = "18565b369bc69d4657d52d6147bdb976453582c3214b1aa5faf347eafb43db9a"

SRC_URI = "http://source.mynonpublic.com/ebox/ebox-dvb-modules-${MACHINE}-${KV}-${SRCDATE}.zip"


S = "${WORKDIR}"

INHIBIT_PACKAGE_STRIP = "1"

do_compile() {
}
do_populate_sysroot() {
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

FILES_${PN} += "${sysconfdir}/modules-load.d/_${MACHINE}.conf /lib/modules/${KV}/extra"