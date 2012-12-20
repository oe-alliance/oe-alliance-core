DESCRIPTION = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

PV = "${KV}+${SRCDATE}"
PR = "r6"

SRC_URI = "http://118.37.185.125/download/drivers/ixuss-one-drivers-${SRCDATE}.tgz"

S = "${WORKDIR}"

INHIBIT_PACKAGE_STRIP = "1"

inherit module

do_compile() {
}

do_install() {
	install -d ${D}/lib/modules/${KV}/extra
	install -d ${D}/${sysconfdir}/modules-load.d
	echo "dvbdrive" >> ${D}/${sysconfdir}/modules-load.d/dvbdrive.conf
	install -m 0755 ${WORKDIR}/dvbdrive.ko ${D}/lib/modules/${KV}/extra
	install -d ${D}/etc/rcS.d
}

