DESCRIPTION = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

SRCDATE = "20120525"
KV = "2.6.18-7.3"
PV = "${KV}+${SRCDATE}"

SRC_URI = "http://en2.ath.cx/release/images/iqon/dev/bcmlinuxdvb_7335-${SRCDATE}.tar.gz"

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
	install -d ${D}/${sysconfdir}/modutils
    for i in `ls ${D}/lib/modules/${KV}/extra | grep \\.ko | sed -e 's/.ko//g'`; do
        echo $i >> ${D}/${sysconfdir}/modutils/_tm
	done
}
