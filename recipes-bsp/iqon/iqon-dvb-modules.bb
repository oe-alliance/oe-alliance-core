SUMMARY = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

SRCDATE = "20131210"
SRCDATE_iqonios200hd = "20140109"
SRCDATE_optimussos2 = "20140109"
KV = "3.5.3"
PV = "${KV}+${SRCDATE}"
PR = "r3"

RCONFLICTS_${PN} = "technomate-dvb-modules"
RREPLACES_${PN} = "technomate-dvb-modules"

SRC_URI = "http://en2.ath.cx/release/images/oedrivers/bcmlinuxdvb_7335-${KV}-${SRCDATE}.tar.gz;name=default"
SRC_URI_iqonios200hd = "http://en2.ath.cx/release/images/oedrivers/ci/bcmlinuxdvbci_7335-${KV}-${SRCDATE}.tar.gz;name=withci"
SRC_URI_optimussos2 = "http://en2.ath.cx/release/images/oedrivers/ci/bcmlinuxdvbci_7335-${KV}-${SRCDATE}.tar.gz;name=withci"

SRC_URI[default.md5sum] = "11f7f782e3436a71ff8518f944e9f4c0"
SRC_URI[default.sha256sum] = "94ff6e8613340197fdc73e5594e1f174fd78bc316222ebf49eee822b019d282a"
SRC_URI[withci.md5sum] = "9cb8e7835caf6f96c430a10798be22ba"
SRC_URI[withci.sha256sum] = "c8ee31dca9c0e5213fb3198ef625e7b4264727959d464101e1dcd2249dc45e14"

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
