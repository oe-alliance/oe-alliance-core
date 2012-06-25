DESCRIPTION = "ViX bootlogo"
SECTION = "base"
PRIORITY = "required"
MAINTAINER = "ViX team"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/openvix-gplv2.inc

RDEPENDS_${PN} += "showiframe"

PV = "${BINARY_VERSION}.${IMAGES_VERSION}"
PR = "r2"

S = "${WORKDIR}/${MACHINE}"

INITSCRIPT_NAME = "bootlogo"
INITSCRIPT_PARAMS = "start 21 S ."

inherit update-rc.d

SRC_URI = "http://enigma2.world-of-satellite.com/git-extras/vix-bootlogos.tgz"

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
	install -m 0644 switchoff.mvi ${D}/usr/share/switchoff.mvi
	install -d ${D}/usr/share/enigma2
	install -m 0644 radio.mvi ${D}/usr/share/enigma2/radio.mvi
	install -d ${D}/${sysconfdir}/init.d
	install -m 0755 ${S}/bootlogo.sh ${D}/${sysconfdir}/init.d/bootlogo
}

do_install_append_vuuno() {
	install -d ${DEPLOY_DIR_IMAGE}
	install -m 0644 ${S}/splash_cfe_auto.bin ${DEPLOY_DIR_IMAGE}/splash_cfe_auto.bin
}
do_install_append_vuultimo() {
	install -d ${DEPLOY_DIR_IMAGE}
	install -m 0644 ${S}/splash_cfe_auto.bin ${DEPLOY_DIR_IMAGE}/splash_cfe_auto.bin
}
do_install_append_et5x00() {
	install -d ${DEPLOY_DIR_IMAGE}
	install -m 644 ${S}/splash.bin ${DEPLOY_DIR_IMAGE}/splash.bin
}
do_install_append_et6x00() {
	install -d ${DEPLOY_DIR_IMAGE}
	install -m 0644 ${S}/splash.bin ${DEPLOY_DIR_IMAGE}/splash.bin
}
do_install_append_et9x00() {
	install -d ${DEPLOY_DIR_IMAGE}
	install -m 0644 ${S}/splash.bin ${DEPLOY_DIR_IMAGE}/splash.bin
}
do_install_append_odinm9() {
	install -d ${DEPLOY_DIR_IMAGE}
	install -m 0644 ${S}/splash.bin ${DEPLOY_DIR_IMAGE}/splash.bin
}
do_install_append_tmtwin() {
	install -d ${DEPLOY_DIR_IMAGE}
	install -m 0644 ${S}/splash.bmp ${DEPLOY_DIR_IMAGE}/splash.bmp
}

FILES_${PN} = "/boot /usr/share /etc/init.d"

SRC_URI[md5sum] = "082b7573ac5c66258e4e3e4bc656aab6"
SRC_URI[sha256sum] = "d25d44405a6e4032177991a5dc8ae3f2820a4be70e93c08ec8fe4c33a4cb6bdd"
