SUMMARY = "Hardware drivers for ${MACHINE_DRIVER}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PV = "${KV}+${SRCDATE}"
PR = "r0"

KV = "4.9.51"
GCCREV = "6.3.0"
SRCDATE = "20180120"

SRC_URI[md5sum] = "96755b50bb6a1bbd919c35d882f94f15"
SRC_URI[sha256sum] = "db4f02de0b6113954041de863e7d28aaf747643d9d64d755f00480a5bd8ebad7"

SRC_URI = "http://source.mynonpublic.com/xtrend/${MACHINE_DRIVER}-drivers-${KV}-${GCCREV}-${SRCDATE}.zip"

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
		echo $i >> ${D}/${sysconfdir}/modules-load.d/_${MACHINE_DRIVER}.conf
	done
}

FILES_${PN} += "${sysconfdir}/modules-load.d/_${MACHINE_DRIVER}.conf /lib/modules/${KV}/extra"
