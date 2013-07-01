DESCRIPTION = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

SRCDATE = "20130614"
KV = "3.5.3"
PV = "${KV}+${SRCDATE}"
PR = "r1"

RCONFLICTS_${PN} = "technomate-dvb-modules"
RREPLACES_${PN} = "technomate-dvb-modules"

SRC_URI = "http://en2.ath.cx/release/images/oedrivers/bcmlinuxdvb_7335-${KV}-${SRCDATE}.tar.gz"

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

SRC_URI[md5sum] = "747b123884a9cc5eeaafd88a2e41a1d9"
SRC_URI[sha256sum] = "d18f52a71ba36c3d34de8609a346992e90eae5ba0af44463652865c8234aefa8"
