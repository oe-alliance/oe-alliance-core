SUMMARY = "Hardware drivers for ${MACHINE_DRIVER}"
SECTION = "base"
PRIORITY = "required"
require conf/license/license-close.inc

inherit module

SRCDATE = "20260407"
KV = "4.4.35"

PV = "${KV}+${SRCDATE}"
PR = "r0"

SRC_URI = "https://source.mynonpublic.com/zgemma/zgemma-mali-drivers-h8se-${KV}-${SRCDATE}.zip"

S = "${UNPACKDIR}"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

FILES:${PN} += "${sysconfdir}/modules-load.d/_${MACHINEBUILD}.conf /lib/modules/${KV}/extra"

do_compile() {
}
do_populate_sysroot() {
}

do_install() {
	install -d ${D}/lib/modules/${KV}/extra
	install -d ${D}/${sysconfdir}/modules-load.d
	install -m 0755 ${S}/mali.ko ${D}${base_libdir}/modules/${KV}/extra
	echo mali >> ${D}/${sysconfdir}/modules-load.d/mali.conf
}

SRC_URI[md5sum] = "71eb07ca7baeba14ab820a8981873c14"
SRC_URI[sha256sum] = "feb1580c8828b1097be587490633e1fc0ca55bf232323a94b66a7cf5568bf5ae"

COMPATIBLE_MACHINE = "h8se"
