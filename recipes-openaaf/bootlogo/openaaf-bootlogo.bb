DESCRIPTION = "openAAF bootlogo"
SECTION = "base"
PRIORITY = "required"
MAINTAINER = "AAF Team"

require conf/license/license-gplv2.inc

RDEPENDS_${PN} += "showiframe"

PV = "2.0"
PR = "r0"

S = "${WORKDIR}"

INITSCRIPT_NAME = "bootlogo"
INITSCRIPT_PARAMS = "start 21 S ."

inherit update-rc.d

SRC_URI = "file://bootlogo.mvi file://bootlogo.jpg file://bootlogo.sh file://splash.bin file://lcdsplash.bin"

BINARY_VERSION = "1"
BINARY_VERSION_mipsel = "10"

IMAGES_VERSION = "1"
IMAGES_VERSION_dm500hd = "2"
IMAGES_VERSION_dm800se = "2"
IMAGES_VERSION_dm7020hd= "2"
IMAGES_VERSION_dm8000 = "2"

SRC_URI += "${@base_contains("MACHINE_FEATURES", "dreambox", "http://sources.dreamboxupdate.com/download/7020/bootlogo-${MACHINE}-${BINARY_VERSION}.elf" , "", d)}"

MVISYMLINKS = "bootlogo_wait backdrop switchoff"

do_install() {
	install -d ${D}/boot
	install -d ${D}/usr/share
	${@base_contains("MACHINE_FEATURES", "dreambox", "install -m 0755 ${S}/bootlogo-${MACHINE}-${BINARY_VERSION}.elf ${D}/boot/bootlogo.elf; install -m 0755 ${S}/bootlogo.jpg ${D}/boot/", "", d)}
	install -m 0755 bootlogo.mvi ${D}/usr/share/bootlogo.mvi
	ln -sf /usr/share/bootlogo.mvi ${D}/boot/bootlogo.mvi
	for i in ${MVISYMLINKS}; do
		ln -sf /boot/bootlogo.mvi ${D}/boot/$i.mvi
		ln -sf /usr/share/bootlogo.mvi ${D}/usr/share/$i.mvi;
	done;
	install -d ${D}/${sysconfdir}/init.d
	install -m 0755 ${S}/bootlogo.sh ${D}/${sysconfdir}/init.d/bootlogo
}

do_install_append_vuuno() {
    install -d ${DEPLOY_DIR_IMAGE}
	install -m 0755 ${S}/splash.bin ${DEPLOY_DIR_IMAGE}/splash_cfe_auto.bin
}
do_install_append_vuultimo() {
    install -d ${DEPLOY_DIR_IMAGE}
	install -m 0755 ${S}/splash.bin ${DEPLOY_DIR_IMAGE}/splash_cfe_auto.bin
}
do_install_append_et5x00() {
    install -d ${DEPLOY_DIR_IMAGE}
	install -m 0755 ${S}/splash.bin ${DEPLOY_DIR_IMAGE}/splash.bin
}
do_install_append_et6x00() {
    install -d ${DEPLOY_DIR_IMAGE}
	install -m 0755 ${S}/splash.bin ${DEPLOY_DIR_IMAGE}/splash.bin
}
do_install_append_et9x00() {
    install -d ${DEPLOY_DIR_IMAGE}
	install -m 0755 ${S}/splash.bin ${DEPLOY_DIR_IMAGE}/splash.bin
}

do_install_append_odinm9() {
    install -d ${DEPLOY_DIR_IMAGE}
	install -m 0755 ${S}/splash.bin ${DEPLOY_DIR_IMAGE}/splash.bin
}

do_install_append_tmtwin() {
    install -d ${DEPLOY_DIR_IMAGE}
	install -m 0755 ${S}/splash.bin ${DEPLOY_DIR_IMAGE}/splash.bmp
}

do_install_append_ventonhdx() {
    install -d ${DEPLOY_DIR_IMAGE}
	install -m 0755 ${S}/splash.bin ${DEPLOY_DIR_IMAGE}/splash.bin
}

do_install_append_gb800se() {
    install -d ${DEPLOY_DIR_IMAGE}
	install -m 0755 ${S}/splash.bin ${DEPLOY_DIR_IMAGE}/splash.bin
}
do_install_append_gb800solo() {
    install -d ${DEPLOY_DIR_IMAGE}
	install -m 0755 ${S}/splash.bin ${DEPLOY_DIR_IMAGE}/splash.bin
}
do_install_append_gb800ue() {
    install -d ${DEPLOY_DIR_IMAGE}
	install -m 0755 ${S}/splash.bin ${DEPLOY_DIR_IMAGE}/splash.bin
	install -m 0755 ${S}/lcdsplash.bin ${DEPLOY_DIR_IMAGE}/lcdsplash.bin
}
do_install_append_gbquad() {
    install -d ${DEPLOY_DIR_IMAGE}
	install -m 0755 ${S}/splash.bin ${DEPLOY_DIR_IMAGE}/splash.bin
	install -m 0755 ${S}/lcdsplash.bin ${DEPLOY_DIR_IMAGE}/lcdsplash.bin
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
