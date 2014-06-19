SUMMARY = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

SRCDATE = "20140618"
KV = "3.9.7"
PV = "${KV}+${SRCDATE}"
PR = "r2"

SRC_URI[md5sum] = "3d8f2c56472466335f132b726ce943be"
SRC_URI[sha256sum] = "3bbacf6a2954442e2f532746147257e4ca05f9332228ec2f95e4bfe6b2f5efcb"

SRC_URI = "http://en3.homeftp.net/release/images/oedrivers/bcmlinuxdvb_7356-${KV}-${SRCDATE}.tar.gz"

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
