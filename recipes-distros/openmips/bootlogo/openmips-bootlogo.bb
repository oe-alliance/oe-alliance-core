DESCRIPTION = "openMips bootlogo"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "openMips"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

RDEPENDS_${PN} += "showiframe"

PV = "1.0"
PR = "r5"

S = "${WORKDIR}"

INITSCRIPT_NAME = "bootlogo"
INITSCRIPT_PARAMS = "start 05 S ."

inherit update-rc.d

SRC_URI = "file://bootlogo.mvi file://backdrop.mvi file://radio.mvi file://bootlogo.sh ${@base_contains("MACHINE_FEATURES", "bootsplash", "file://splash.bin" , "", d)}"

FILES_${PN} = "/boot /usr/share /etc/init.d"

do_install() {
	install -d ${D}/usr/share
	install -m 0644 bootlogo.mvi ${D}/usr/share/bootlogo.mvi
	install -m 0644 backdrop.mvi ${D}/usr/share/backdrop.mvi
	install -d ${D}/usr/share/enigma2
	install -m 0644 radio.mvi ${D}/usr/share/enigma2/radio.mvi
	install -d ${D}/${sysconfdir}/init.d
	install -m 0755 ${S}/bootlogo.sh ${D}/${sysconfdir}/init.d/bootlogo
}

inherit deploy
do_deploy() {
	if [ -e splash.bin ]; then
		install -m 0644 splash.bin ${DEPLOYDIR}/${BOOTLOGO_FILENAME}
	fi
	if [ -e lcdsplash.bin ]; then
		install -m 0644 lcdsplash.bin ${DEPLOYDIR}/lcdsplash.bin
	fi
}

addtask deploy before do_build after do_install
