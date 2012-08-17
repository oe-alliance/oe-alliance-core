DESCRIPTION = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

SRCDATE = "20120817"
KV = "3.5.1"
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
		echo $i _hwtype=\$hwtypenum >> ${D}/${sysconfdir}/modules-load.d/_${MACHINE}.conf

	done
}

SRC_URI[md5sum] = "f6eeae8bfae0d07c7083d554ba4d5e37"
SRC_URI[sha256sum] = "f7415d8ed706cbee1dae9cf6943b2a7531efafb8cc16ba5c530073aa91868aed"
