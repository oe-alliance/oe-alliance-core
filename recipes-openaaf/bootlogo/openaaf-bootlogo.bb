DESCRIPTION = "openAAF bootlogo"
SECTION = "base"
PRIORITY = "required"
MAINTAINER = "AAF Team"

require conf/license/license-gplv2.inc

RDEPENDS_${PN} += "showiframe"

PV = "2.0"
PR = "r5"

S = "${WORKDIR}"

INITSCRIPT_NAME = "bootlogo"
INITSCRIPT_PARAMS = "start 05 S ."

inherit update-rc.d

SRC_URI = "file://bootlogo.mvi file://bootlogo.jpg file://bootlogo.sh file://splash.bin file://lcdsplash.bin"

BINARY_VERSION = "1"
BINARY_VERSION_mipsel = "10"

IMAGES_VERSION = "1"
IMAGES_VERSION_dm500hd = "2"
IMAGES_VERSION_dm800se = "2"
IMAGES_VERSION_dm7020hd = "2"
IMAGES_VERSION_dm8000 = "2"

SRC_URI += "${@base_contains("MACHINE_FEATURES", "dreambox", "http://sources.dreamboxupdate.com/download/7020/bootlogo-${MACHINE}-${BINARY_VERSION}.elf;name=bootlogo-${MACHINE}-${BINARY_VERSION}" , "", d)}"

SRC_URI[bootlogo-dm800-10.md5sum] = "0ad1cee22268ecb6e71ca6f2e129d48a"
SRC_URI[bootlogo-dm800-10.sha256sum] = "890c529fa06f3d4f83536c2423fc3a09c7134756f578018f10823d76492e3307"
SRC_URI[bootlogo-dm500hd-10.md5sum] = "0e43d1d11914a331793ef119effdc6ef"
SRC_URI[bootlogo-dm500hd-10.sha256sum] = "ac9c7ae0a487f5712712d88dc139ae6505d83b3cd8950aa7d0367d84ba6ff071"
SRC_URI[bootlogo-dm800se-10.md5sum] = "826addc0d096c8caf81b406cc5c13085"
SRC_URI[bootlogo-dm800se-10.sha256sum] = "43da0c972adee37e24caa654bebea4e17c6b1ef27d73e886dfc81f9d823d1685"
SRC_URI[bootlogo-dm7020hd-10.md5sum] = "2f37855226ccdbf818e7b2aa5240fc26"
SRC_URI[bootlogo-dm7020hd-10.sha256sum] = "f5c9551d4af70b0f6360ce193319d2516b1a6c5dbd38b84ba238fe9efff71f34"
SRC_URI[bootlogo-dm8000-10.md5sum] = "8dab446be9dfd09751e66601b629b3c0"
SRC_URI[bootlogo-dm8000-10.sha256sum] = "6a7a54c0c823d92772bb09c87fdf5f385ea11e8a7ab99f26a042ddd834276ca0"

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
