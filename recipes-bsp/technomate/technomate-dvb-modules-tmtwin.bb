DESCRIPTION = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

SRCDATE = "20120720"
KV = "3.4.6"
PV = "${KV}-${SRCDATE}"

SRC_URI = "http://en2.ath.cx/release/images/iqon/dev/bcmlinuxdvb_7335-${PV}.tar.gz"

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

SRC_URI[md5sum] = "490cbf118cce49a9317d01be97440a9a"
SRC_URI[sha256sum] = "b0c4789d22ed565e4d401708229b04d9cee963fa73120b528da4f32d7aa22a2d"
