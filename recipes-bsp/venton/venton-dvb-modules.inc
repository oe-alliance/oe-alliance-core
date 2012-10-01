DESCRIPTION = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

PR = "r5"

PV = "${KV}+${SRCDATE}"
MACHINE_KERNEL_PR_append = ".0"

RDEPENDS_${PN} += "venton-fpupdate-hdx"

S = "${WORKDIR}"

PACKAGE_STRIP = "no"

inherit module

do_compile() {
}

do_install() {
	install -d ${D}/lib/modules/${KV}/extra
	install -d ${D}/${sysconfdir}/modutils
	for i in dvb; do
		install -m 0755 ${WORKDIR}/$i.ko ${D}/lib/modules/${KV}/extra
		echo $i >> ${D}/${sysconfdir}/modutils/_${MACHINE}
	done
}
