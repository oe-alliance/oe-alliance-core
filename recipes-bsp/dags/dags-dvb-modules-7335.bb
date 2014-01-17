SUMMARY = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

SRCDATE = "20140114"
KV = "3.9.7"
PV = "${KV}+${SRCDATE}"
PR = "r0"

SRC_URI = "http://en2.ath.cx/release/images/oedrivers/bcmlinuxdvb_7335-${KV}-${SRCDATE}.tar.gz"

SRC_URI[md5sum] = "4bc8ec825411fa4615239fac9de1fa12"
SRC_URI[sha256sum] = "d862f1c0d5b980ef98e943fdd09af5a4a2aaa67b836e7a5f6232c506d92fc311"

S = "${WORKDIR}"

INHIBIT_PACKAGE_STRIP = "1"

inherit module

do_compile() {
}

do_install() {
    install -d ${D}/lib/modules/${KV}/extra
    for f in lib/modules/${KV}/extra/*.ko; do
        install -m 0644 $f ${D}/$f;
    done
    install -d ${D}/${sysconfdir}/modules-load.d
    for i in `ls ${D}/lib/modules/${KV}/extra | grep \\.ko | sed -e 's/.ko//g'`; do
        echo $i _hwtype=\$hwtypenum >> ${D}/${sysconfdir}/modules-load.d/_${MACHINE}.conf
    done
}

FILES_${PN} += "${sysconfdir}/modules-load.d/_${MACHINE}.conf"
