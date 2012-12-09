DESCRIPTION = "openAAF bootlogo"
SECTION = "base"
PRIORITY = "required"
MAINTAINER = "AAF Team"

require conf/license/license-gplv2.inc

RDEPENDS_${PN} += "showiframe"

PV = "2.0"
PR = "r18"

S = "${WORKDIR}"

INITSCRIPT_NAME = "bootlogo"
INITSCRIPT_PARAMS = "start 05 S ."

inherit update-rc.d

SRC_URI = "file://bootlogo.mvi file://bootlogo.jpg file://bootlogo.sh file://splash.bin file://splash600.bin file://lcdsplash.bin file://radio.mvi"

BINARY_VERSION = "1.3"

SRC_URI += "${@base_contains("MACHINE_FEATURES", "dreambox", "http://dreamboxupdate.com/download/opendreambox/2.0.0/dreambox-bootlogo/dreambox-bootlogo_${BINARY_VERSION}_${MACHINE_ARCH}.tar.bz2;name=${MACHINE_ARCH}" , "", d)}"

SRC_URI[dm800.md5sum] = "0aacd07cc4d19b388c6441b007e3525a"
SRC_URI[dm800.sha256sum] = "978a7c50fd0c963013477b5ba08462b35597ea130ae428c828bfcbb5c7cf4cac"
SRC_URI[dm8000.md5sum] = "1b63ac7e2bd5c0db0748606acc310d47"
SRC_URI[dm8000.sha256sum] = "91e4402190fe88cf394ae780141d968a1ebecd8db7b23c1f0ca3f4bfa9c9512a"
SRC_URI[dm800se.md5sum] = "3413a894a3d77e02cae34b96d302817d"
SRC_URI[dm800se.sha256sum] = "8a283442c231e82ee1a2093e53dc5bf52c478e12d22c79af7e7026b52711fc9c"
SRC_URI[dm500hd.md5sum] = "b9ada70304ca1f9a8e36a55bd38834c6"
SRC_URI[dm500hd.sha256sum] = "d4b0f650711d5d6fdecb42efe9e13987ef803cba829d0950e899608a784ae3b2"
SRC_URI[dm7020hd.md5sum] = "f8e423dbf7661367659fa86a68b74bc4"
SRC_URI[dm7020hd.sha256sum] = "118d7bb57c4b41dd45c7bdd9a056a0745454f42092692fb4309997e035eb6908"

MVISYMLINKS = "bootlogo_wait backdrop switchoff"

do_install() {
	install -d ${D}/boot
	install -d ${D}/usr/share
	${@base_contains("MACHINE_FEATURES", "dreambox", "install -m 0755 ${S}/dreambox-bootlogo_${BINARY_VERSION}_${MACHINE_ARCH}/bootlogo-${MACHINE_ARCH}.elf.gz ${D}/boot/; install -m 0755 ${S}/dreambox-bootlogo_${BINARY_VERSION}_${MACHINE_ARCH}/bootlogo-${MACHINE_ARCH}.jpg ${D}/boot/", "", d)}
	install -m 0755 bootlogo.mvi ${D}/usr/share/bootlogo.mvi
	ln -sf /usr/share/bootlogo.mvi ${D}/boot/bootlogo.mvi
	for i in ${MVISYMLINKS}; do
		ln -sf /boot/bootlogo.mvi ${D}/boot/$i.mvi
		ln -sf /usr/share/bootlogo.mvi ${D}/usr/share/$i.mvi;
	done;
	install -d ${D}/usr/share/enigma2
	install -m 0755 ${S}/radio.mvi ${D}/usr/share/enigma2/radio.mvi
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
do_install_append_et4x00() {
	install -d ${DEPLOY_DIR_IMAGE}
	install -m 0755 ${S}/splash600.bin ${DEPLOY_DIR_IMAGE}/splash.bin
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
do_install_append_tm2t() {
	install -d ${DEPLOY_DIR_IMAGE}
	install -m 0755 ${S}/splash.bin ${DEPLOY_DIR_IMAGE}/splash.bmp
}
do_install_append_tmsingle() {
	install -d ${DEPLOY_DIR_IMAGE}
	install -m 0755 ${S}/splash.bin ${DEPLOY_DIR_IMAGE}/splash.bmp
}

do_install_append_ventonhdx() {
	install -d ${DEPLOY_DIR_IMAGE}
	install -m 0755 ${S}/splash.bin ${DEPLOY_DIR_IMAGE}/splash.bin
}
do_install_append_ventonhde() {
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
do_install_append_xp1000() {
	install -d ${DEPLOY_DIR_IMAGE}
	install -m 0755 ${S}/splash.bin ${DEPLOY_DIR_IMAGE}/splash.bin
}

do_install_append_odinm7() {
	install -d ${DEPLOY_DIR_IMAGE}
	install -m 0755 ${S}/splash.bin ${DEPLOY_DIR_IMAGE}/splash.bin
}

do_install_append_ixussone() {
	install -d ${DEPLOY_DIR_IMAGE}
	install -m 0644 ${S}/splash.bin ${DEPLOY_DIR_IMAGE}/cfe-bootlogo.bmp
}


pkg_preinst() {
	if grep dm /etc/hostname > /dev/null ; then
		if [ -z "$D" ]
		then
			if mountpoint -q /boot
			then
				mount -o remount,rw,compr=none /boot
			else
				mount -t jffs2 -o rw,compr=none mtd:'boot partition' /boot
			fi
		fi
	fi
}

pkg_postinst() {
	if grep dm /etc/hostname > /dev/null ; then
		if [ -z "$D" ]
		then
			umount /boot
		fi
	fi
}

pkg_prerm() {
	if grep dm /etc/hostname > /dev/null ; then
		if [ -z "$D" ]
		then
			if mountpoint -q /boot
			then
				mount -o remount,rw,compr=none /boot
			else
				mount -t jffs2 -o rw,compr=none mtd:'boot partition' /boot
			fi
		fi
	fi	
}

pkg_postrm() {
	if grep dm /etc/hostname > /dev/null ; then
		if [ -z "$D" ]
		then
			umount /boot
		fi
	fi	
}

PACKAGE_ARCH := "${MACHINE_ARCH}"
FILES_${PN} = "/boot /usr/share /etc/init.d"
