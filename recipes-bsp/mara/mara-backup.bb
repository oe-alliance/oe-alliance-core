DESCRIPTION = "mara backup"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "GPL"

require conf/license/license-gplv2.inc

PV = "1.0"
PR = "r3"

SRC_URI = "file://mara-backup.sh \
		   "

do_install() {
	install -d ${D}${sysconfdir}/init.d
	install -d ${D}${sysconfdir}/rc3.d
	install -m 0755 ${WORKDIR}/mara-backup.sh ${D}${sysconfdir}/init.d
	ln -sf	../init.d/mara-backup.sh ${D}${sysconfdir}/rc3.d/S30mara-backup
}

PACKAGE_ARCH := "${MACHINE_ARCH}"