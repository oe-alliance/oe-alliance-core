DESCRIPTION = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

SRCDATE = "20121208"
KV = "3.5.3"
PV = "${KV}+${SRCDATE}"
PR = "r0"

RCONFLICTS_${PN} = "technomate-dvb-modules-tmtwin"
RREPLACES_${PN} = "technomate-dvb-modules-tmtwin"

SRC_URI = "http://en2.ath.cx/release/images/iqon/dev/bcmlinuxdvb_7335-${KV}-${SRCDATE}.tar.gz"

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

SRC_URI[md5sum] = "afdf18e9746388e76e3de3f30d869074"
SRC_URI[sha256sum] = "04685b8302871641b4e805c905401f3667199bab61bbbb428678b70966c7c978"
