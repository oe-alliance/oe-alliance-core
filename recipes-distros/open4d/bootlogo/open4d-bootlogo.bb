DESCRIPTION = "openViX bootlogo"
SECTION = "base"
PRIORITY = "required"
MAINTAINER = "openViX"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

RDEPENDS_${PN} += "showiframe"

PV = "${BINARY_VERSION}.${IMAGES_VERSION}"
PR = "r4"

S = "${WORKDIR}"

INITSCRIPT_NAME = "bootlogo"
INITSCRIPT_PARAMS = "start 05 S ."

inherit update-rc.d

SRC_URI = "file://bootlogo.mvi file://backdrop.mvi file://radio.mvi file://bootlogo.sh"
SRC_URI_append_gb800se = "file://splash.bin"
SRC_URI_append_gb800solo = "file://splash.bin"
SRC_URI_append_gb800ue = "file://splash.bin file://lcdsplash.bin"
SRC_URI_append_gbquad = "file://splash.bin file://lcdsplash.bin"
SRC_URI_append_vuuno = " file://splash_cfe_auto.bin"
SRC_URI_append_vuultimo = " file://splash_cfe_auto.bin"
SRC_URI_append_et4x00 = " file://splash.bin"
SRC_URI_append_et5x00 = " file://splash.bin"
SRC_URI_append_et6x00 = " file://splash.bin"
SRC_URI_append_et9x00 = " file://splash.bin"
SRC_URI_append_odinm9 = " file://splash.bin"
SRC_URI_append_ventonhdx = " file://splash.bin"
SRC_URI_append_ventonhde = " file://splash.bin"
SRC_URI_append_tmtwin = " file://splash.bmp"
SRC_URI_append_tm2t = " file://splash.bmp"
SRC_URI_append_tmsingle = " file://splash.bmp"

BINARY_VERSION = "1"
BINARY_VERSION_mipsel = "8"
IMAGES_VERSION = "1"

SRC_URI += "${@base_contains("MACHINE_FEATURES", "dreambox", "http://sources.dreamboxupdate.com/download/7020/bootlogo-${MACHINE}-${BINARY_VERSION}.elf" , "", d)}"

do_install() {
	${@base_contains("MACHINE_FEATURES", "dreambox", "install -d ${D}/boot", "", d)}
	${@base_contains("MACHINE_FEATURES", "dreambox", "install -m 0755 ${S}/bootlogo-${MACHINE}-${BINARY_VERSION}.elf ${D}/boot/bootlogo.elf; install -m 0755 ${S}/bootlogo.jpg ${D}/boot/", "", d)}
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
		install -m 0644 splash.bin ${DEPLOYDIR}/splash.bin
	fi
	if [ -e lcdsplash.bin ]; then
		install -m 0644 lcdsplash.bin ${DEPLOYDIR}/lcdsplash.bin
	fi
	if [ -e splash_cfe_auto.bin ]; then
		install -m 0644 splash_cfe_auto.bin ${DEPLOYDIR}/splash_cfe_auto.bin
	fi
	if [ -e splash.bmp ]; then
		install -m 0644 splash.bmp ${DEPLOYDIR}/splash.bmp
	fi
}

addtask deploy before do_build after do_install

FILES_${PN} = "/boot /usr/share /etc/init.d"
