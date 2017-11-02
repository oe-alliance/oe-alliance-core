SUMMARY = "Hardware drivers for ${MACHINE_DRIVER}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PV = "${KV}+${SRCDATE}"
PR = "r0"

KV = "4.9.9"
GCCREV = "6.3.0"
SRCDATE = "20171101"

SRC_URI[md5sum] = "174a6950f446ad8183475e3ee8c7408a"
SRC_URI[sha256sum] = "fe9b6e3c142dfab8d35c8088207bd971e3f460226ff265902b2977aec4a535cd"

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
