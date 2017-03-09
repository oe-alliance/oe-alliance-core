SUMMARY = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PV = "${KV}+${SRCDATE}"
PR = "r0"

KV = "4.1.37"
GCCREV = "6.3.0"
SRCDATE = "20170309"

SRC_URI[md5sum] = "d3cfc9989d24342b984565febf0cc372"
SRC_URI[sha256sum] = "2ea997712b1fe59a7c5439ee26aa9c5f9981aea9acf39de6d73eb52f268f2c44"

SRC_URI = "http://gi-et.info/${MACHINE}/${MACHINE}-drivers-${KV}-${GCCREV}-${SRCDATE}.zip"

S = "${WORKDIR}"

INHIBIT_PACKAGE_STRIP = "1"

do_compile() {
}
do_populate_sysroot() {
}

do_install() {
	install -d ${D}/lib/modules/${KV}/extra
	install -d ${D}/${sysconfdir}/modules-load.d
	for i in tpm modloader modloader2 dvb; do
		install -m 0755 ${WORKDIR}/$i.ko ${D}/lib/modules/${KV}/extra
		echo $i >> ${D}/${sysconfdir}/modules-load.d/_${MACHINE}.conf
	done
}

FILES_${PN} += "${sysconfdir}/modules-load.d/_${MACHINE}.conf /lib/modules/${KV}/extra"
