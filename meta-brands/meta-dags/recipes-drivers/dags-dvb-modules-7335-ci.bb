SUMMARY = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

SRCDATE = "20140627"
KV = "3.9.7"
PV = "${KV}+${SRCDATE}"
PR = "r0"

SRC_URI = "http://en3.homeftp.net/release/images/oedrivers/bcmlinuxdvb_7335-${KV}-1ci-${SRCDATE}.tar.gz"

SRC_URI[md5sum] = "83637cb3de3f11fb12b0ec37cd1358fd"
SRC_URI[sha256sum] = "002fc12503a1142cee93bbf24260a3370bf048868c7fbd8f567bed9073e3c302"

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
