SUMMARY = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

SRCDATE = "20140428"
KV = "3.9.7"
PV = "${KV}+${SRCDATE}"
PR = "r0"

SRC_URI = "http://en3.homeftp.net/release/images/oedrivers/bcmlinuxdvb_7335-${KV}-1ci-${SRCDATE}.tar.gz"

SRC_URI[md5sum] = "7006cf216d1bb83e071624632cf3c9b0"
SRC_URI[sha256sum] = "3592117190c48b8ca04bac2086c4e74e52c27f8bf37a318f2f8f871d5f7cada4"

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
