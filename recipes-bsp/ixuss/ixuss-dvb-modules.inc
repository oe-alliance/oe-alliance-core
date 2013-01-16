DESCRIPTION = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

PV = "${KV}+${SRCDATE}"
PR = "r10"

#SRC_URI = "http://118.37.185.125/download/drivers/ixuss-one-drivers-${SRCDATE}.tgz"
#SRC_URI = "http://www.alisat.info/app/IXUSS%20ONE%20by%20Medialink/Source%20Code/ixuss-one-drivers-${SRCDATE}.tgz"
SRC_URI = "http://source.mynonpublic.com/ixuss-one-drivers-${SRCDATE}.tgz"

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

