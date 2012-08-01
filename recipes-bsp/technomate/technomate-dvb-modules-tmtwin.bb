DESCRIPTION = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

SRCDATE = "20120801"
KV = "3.4.6"
PV = "${KV}+${SRCDATE}"
PR = "r0"

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
		echo $i _hwtype=$hwtypenum >> ${D}/${sysconfdir}/modules-load.d/_${MACHINE}.conf

	done
}

SRC_URI[md5sum] = "259e6d054e10e43f0fdfcf223e351def"
SRC_URI[sha256sum] = "e12325b0a3083e1dfd09400a8295472708118c226d86f7bb9c7b0a30cfbd60b8"
