SUMMARY = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

SRCDATE = "20140425"
KV = "3.9.7"
PV = "${KV}+${SRCDATE}"
PR = "r0"

SRC_URI = "http://en3.homeftp.net/release/images/oedrivers/bcmlinuxdvb_7335-${KV}-${SRCDATE}.tar.gz"

SRC_URI[md5sum] = "d3c3971f135dba67c9a511f581709eae"
SRC_URI[sha256sum] = "0e2b3eace990dcb0cb5e20e96ebabfae2e969e5be616cccf42999aa54303187d"

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
