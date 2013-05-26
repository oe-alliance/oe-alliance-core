DESCRIPTION = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

SRCDATE = "20130220"
KV = "3.5.3"
PV = "${KV}+${SRCDATE}"
PR = "r1"

RCONFLICTS_${PN} = "technomate-dvb-modules"
RREPLACES_${PN} = "technomate-dvb-modules"

SRC_URI = "http://en2.ath.cx/release/images/iqon/dev/bcmlinuxdvb_7335-${KV}-${SRCDATE}.tar.gz;name=default"
SRC_URI_tmnano = "http://en2.ath.cx/release/images/iqon/dev/bcmlinuxdvb_7335-${KV}-20130520.tar.gz;name=tmnano"

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

SRC_URI[default.md5sum] = "1921b69f30657df93ff1bb05a6764847"
SRC_URI[default.sha256sum] = "8aaef78ac0bc078c8cbd5187edc1127f4ba0920fc32978790b67a9d8101dc0eb"

SRC_URI[tmnano.md5sum]  = "764f933f202eaa40b5afee4b5dd7ce58"
SRC_URI[tmnano.sha256sum] = "118b0eee0e897b2c1bc3ce0590676f81363a32db68c9bfda625e6a8c60b58d08"
