DESCRIPTION = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

SRCDATE = "20120921"
KV = "3.5.3"
PV = "${KV}+${SRCDATE}"
PR = "r1"

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

SRC_URI[md5sum] = "e4999bd8ea2672ac3ee6793da48d50d8"
SRC_URI[sha256sum] = "7ea0f41049dbe238c4173263443e295ab65cd529fb379d0e88a16c429e4a6dd2"
