DESCRIPTION = "iclass backup"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "GPL"

require conf/license/license-gplv2.inc

PV = "1.0"
PR = "r0"

SRC_URI = "file://iclass-backup.sh \
		   "

do_install() {
	install -d ${D}${sysconfdir}/init.d
	install -d ${D}${sysconfdir}/rc3.d
	install -m 0755 ${WORKDIR}/iclass-backup.sh ${D}${sysconfdir}/init.d
	ln -sf	../init.d/iclass-backup.sh ${D}${sysconfdir}/rc3.d/S30iclass-backup
}

PACKAGE_ARCH := "${MACHINE_ARCH}"