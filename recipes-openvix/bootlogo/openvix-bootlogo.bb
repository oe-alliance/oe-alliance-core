DESCRIPTION = "openViX bootlogo"
SECTION = "base"
PRIORITY = "required"
MAINTAINER = "openViX"

require conf/license/license-gplv2.inc

RDEPENDS_${PN} += "showiframe"

PV = "${BINARY_VERSION}.${IMAGES_VERSION}"
PR = "r4"

S = "${WORKDIR}/${MACHINE}"

INITSCRIPT_NAME = "bootlogo"
INITSCRIPT_PARAMS = "start 21 S ."

inherit update-rc.d

SRC_URI = "bootlogo.mvi backdrop.mvi radio.mvi bootlogo.sh"

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

SRC_URI[md5sum] = "e0447975ab957e77bd0d47ab0186a806"
SRC_URI[sha256sum] = "6de5ffc6bc841331b8b256c5bd7ca06a0af945d2fa8715ca6828d6cea9cdfdd9"
