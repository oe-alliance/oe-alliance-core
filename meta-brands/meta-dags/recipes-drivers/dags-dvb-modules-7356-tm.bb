SUMMARY = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRCDATE = "20141120"
KV = "3.9.7"
PV = "${KV}+${SRCDATE}"
PR = "r3"

SRC_URI[md5sum] = "871e01ba6cb210aa6290db5b74b1b10c"
SRC_URI[sha256sum] = "39762ff872b5c67a0c4243376567f775e2ca5c06f15fc221c07230a0b5e99ea5"

SRC_URI = "http://en3homeftp.net/release/images/oedrivers/bcmlinuxdvb_7356-${KV}-tmnano3t-1080-${SRCDATE}.tar.gz"

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
