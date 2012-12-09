DESCRIPTION = "SIFTeam bootlogo"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "SIFTeam"

require conf/license/license-gplv2.inc

RDEPENDS_${PN} += "showiframe"

PV = "1.0"
PR = "r8"

S = "${WORKDIR}/"

INITSCRIPT_NAME = "bootlogo"
INITSCRIPT_PARAMS = "start 05 S ."

inherit update-rc.d

# This needs a small explanation; when the machine has 'switchoff' support, it switches itself off, so we don't need switchoff.mvi...
SWITCHOFFMVI = "${@base_contains("MACHINE_FEATURES", "switchoff", "" , "switchoff.mvi", d)}"

SRC_URI = " \
		file://bootlogo.mvi \
		file://switchoff.mvi \
		file://bootlogo.jpg \
		file://bootlogo.sh \
		file://splash.bin \
		"

SRC_URI_append_gb800ue = " \
				file://lcdsplash.bin \
				"
SRC_URI_append_gbquad = " \
				file://lcdsplash.bin \
				"

BINARY_VERSION = "1"
BINARY_VERSION_mipsel = "9"

IMAGES_VERSION = "1"

SRC_URI += "${@base_contains("MACHINE_FEATURES", "dreambox", "http://sources.dreamboxupdate.com/download/7020/bootlogo-${MACHINE}-${BINARY_VERSION}.elf;name=bootlogo-${MACHINE}-${BINARY_VERSION}" , "", d)}"
SRC_URI[bootlogo-dm500hd-9.md5sum] = "9ffb05b39b89a3be1b9eebc523d7bc69"
SRC_URI[bootlogo-dm500hd-9.sha256sum] = "ad1dce606f00a077f7d6433217d7c9a7ef9b849891983758e4e3745445ca8aea"
SRC_URI[bootlogo-dm7020hd-9.md5sum] = "aedb57715e4ea7fe29bbe3a0ac6498cc"
SRC_URI[bootlogo-dm7020hd-9.sha256sum] = "abda2e995a33fa3e4fef4a1470a71e912b43857f06b226780834c29db35ed583"
SRC_URI[bootlogo-dm8000-9.md5sum] = "5b7aa440ef459b2470fe45af9e123811"
SRC_URI[bootlogo-dm8000-9.sha256sum] = "81363d7ab6497da8a905080871ebc8268cf7a946d4ea0fa18d6f126ed77e13b7"
SRC_URI[bootlogo-dm800-9.md5sum] = "92220b11663fad8b3f91f3a7ae986195"
SRC_URI[bootlogo-dm800-9.sha256sum] = "0272e9e2f7828eb89345360dc41ccecdca9b1a5504047996061dec4a66527538"
SRC_URI[bootlogo-dm800se-9.md5sum] = "208720ff116306a2f0eb6b8962325da9"
SRC_URI[bootlogo-dm800se-9.sha256sum] = "724425bb280280ec0be6913f05840af385aee3f07318df7885b74c50353fdff6"

MVI = "${SWITCHOFFMVI}"
MVISYMLINKS = "bootlogo_wait backdrop"

do_install() {
	install -d ${D}/boot
	install -d ${D}/usr/share
	${@base_contains("MACHINE_FEATURES", "dreambox", "install -m 0755 ${S}/bootlogo-${MACHINE}-${BINARY_VERSION}.elf ${D}/boot/bootlogo.elf; install -m 0755 ${S}/bootlogo.jpg ${D}/boot/", "", d)}
	install -m 0755 bootlogo.mvi ${D}/usr/share/bootlogo.mvi
	ln -sf /usr/share/bootlogo.mvi ${D}/boot/bootlogo.mvi
	for i in ${MVI}; do
		install -m 0755 ${S}/$i ${D}/usr/share/
		ln -sf /usr/share/$i ${D}/boot/$i
	done;
	for i in ${MVISYMLINKS}; do
		ln -sf /boot/bootlogo.mvi ${D}/boot/$i.mvi
		ln -sf /usr/share/bootlogo.mvi ${D}/usr/share/$i.mvi;
	done;
	install -d ${DEPLOY_DIR_IMAGE}
	install -m 0755 ${S}/splash.bin ${DEPLOY_DIR_IMAGE}/splash.bin
	install -d ${D}/${sysconfdir}/init.d
	install -m 0755 ${S}/bootlogo.sh ${D}/${sysconfdir}/init.d/bootlogo
}

do_install_append_gb800ue() {
	install -m 0755 ${S}/lcdsplash.bin ${DEPLOY_DIR_IMAGE}/lcdsplash.bin
}

do_install_append_gbquad() {
	install -m 0755 ${S}/lcdsplash.bin ${DEPLOY_DIR_IMAGE}/lcdsplash.bin
}

do_install_append_vuuno() {
	mv ${DEPLOY_DIR_IMAGE}/splash.bin ${DEPLOY_DIR_IMAGE}/splash_cfe_auto.bin
}

do_install_append_vuultimo() {
	mv ${DEPLOY_DIR_IMAGE}/splash.bin ${DEPLOY_DIR_IMAGE}/splash_cfe_auto.bin
}

do_install_append_tmtwin() {
	mv ${DEPLOY_DIR_IMAGE}/splash.bin ${DEPLOY_DIR_IMAGE}/splash.bmp
}
do_install_append_tm2t() {
	mv ${DEPLOY_DIR_IMAGE}/splash.bin ${DEPLOY_DIR_IMAGE}/splash.bmp
}
do_install_append_tmsingle() {
	mv ${DEPLOY_DIR_IMAGE}/splash.bin ${DEPLOY_DIR_IMAGE}/splash.bmp
}

pkg_preinst() {
	[ -d /proc/stb ] && mount -t jffs2 mtd:'boot partition' /boot
	true
}

pkg_postinst() {
	[ -d /proc/stb ] && umount /boot
	true
}

pkg_prerm() {
	[ -d /proc/stb ] && mount -t jffs2 mtd:'boot partition' /boot
	true
}

pkg_postrm() {
	[ -d /proc/stb ] && umount /boot
	true
}

PACKAGE_ARCH := "${MACHINE_ARCH}"
FILES_${PN} = "/boot /usr/share /etc/init.d"
