DESCRIPTION = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

SRCDATE = "20131217"
KV = "3.9.7"
PV = "${KV}+${SRCDATE}"
PR = "r1"

SRC_URI[md5sum] = "fba9359f23fe227073606c9c2c4a51ae"
SRC_URI[sha256sum] = "59113ece17f57eb48dbe0b9d7d7cd2dba4e08635f96ae569c511ebab3da4e32f"

SRC_URI = "http://en2.ath.cx/release/images/oedrivers/bcmlinuxdvb_7356-${KV}-${SRCDATE}.tar.gz"

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