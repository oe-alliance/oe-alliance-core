DESCRIPTION = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

KV = "3.3.0"
SRCDATE = "20120331"

PV = "${KV}+${SRCDATE}"
MACHINE_KERNEL_PR_append = ".1"

RDEPENDS_${PN} += "et-fpupdate"

SRC_URI = "http://www.et-view.com/download/drivers/${MACHINE}-drivers-${KV}-${SRCDATE}.zip"

SRC_URI[md5sum] = "1986366e1b5b5c430e634f0fe6cd150d"
SRC_URI[sha256sum] = "043452fdd04a8165a0edce9cbad7f08f6fbb5a68410a5ce06797cf04e5d83f3a"

S = "${WORKDIR}"

INHIBIT_PACKAGE_STRIP = "1"

inherit module

do_compile() {
}

do_install() {
	install -d ${D}/lib/modules/${KV}/extra
	install -d ${D}/${sysconfdir}/modules-load.d
	for i in tpm modloader modloader2 dvb; do
		install -m 0755 ${WORKDIR}/$i.ko ${D}/lib/modules/${KV}/extra
		echo $i >> ${D}/${sysconfdir}/modules-load.d/_${MACHINE}.conf
	done
}
