SUMMARY = "OpenHDF bootlogo"
SECTION = "base"
PRIORITY = "required"
MAINTAINER = "OpenHDF Team"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

RDEPENDS_${PN} += "showiframe"

PV = "${IMAGE_VERSION}"
PR = "r4"

S = "${WORKDIR}"

INITSCRIPT_NAME = "bootlogo"
INITSCRIPT_PARAMS = "start 06 S ."
PRECOMPILED_ARCH = "${MACHINE}"
PRECOMPILED_ARCH_dm7020hdv2 = "dm7020hd"

inherit update-rc.d

SRC_URI = "file://bootlogo.mvi file://radio.mvi file://bootlogo.sh file://splash576.bmp file://splash480.bmp file://splash1280.jpg \
    ${@bb.utils.contains("MACHINE_FEATURES", "gigabluelcd220", "file://lcdsplash220.bin file://lcdwaitkey220.bin file://lcdwarning220.bin" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "gigabluelcd400", "file://lcdsplash400.bin file://lcdwaitkey400.bin file://lcdwarning400.bin" , "", d)} \
"

SRC_URI_append_dags7356 = "file://splash1.bmp file://splash1_os1.bmp file://splash1_os2.bmp file://splash2.bmp file://splash3.bmp"
SRC_URI_append_dags7362 = "file://splash1_power.bmp file://splash1_os1.bmp file://splash1_os2.bmp file://splash2.bmp file://splash3.bmp"
SRC_URI_append_7100s = "file://lcdsplash220.bin file://7100s/lcdwaitkey220.bin file://7100s/lcdwarning220.bin file://7100s/lcdcomplete220.bin"
SRC_URI_append_7210s = "file://lcdsplash220.bin file://7100s/lcdwaitkey220.bin file://7100s/lcdwarning220.bin file://7100s/lcdcomplete220.bin"
SRC_URI_append_7105s = "file://lcdsplash220.bin file://7100s/lcdwaitkey220.bin file://7100s/lcdwarning220.bin file://7100s/lcdcomplete220.bin"
SRC_URI_append_7215s = "file://lcdsplash220.bin file://7100s/lcdwaitkey220.bin file://7100s/lcdwarning220.bin file://7100s/lcdcomplete220.bin"

BINARY_VERSION = "1.3"

SRC_URI += "${@bb.utils.contains("MACHINE_FEATURES", "dreamboxv1", "http://dreamboxupdate.com/download/opendreambox/2.0.0/dreambox-bootlogo/dreambox-bootlogo_${BINARY_VERSION}_${PRECOMPILED_ARCH}.tar.bz2;name=${PRECOMPILED_ARCH}" , "", d)}"

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
SRC_URI[dm800sev2.md5sum] = "a570f8f2eb4d7800a2fa2db60d81b58e"
SRC_URI[dm800sev2.sha256sum] = "af522a5d4dc75507f2cd96582a270236fedade35b8dca74c0f75d999ffb210bf"
SRC_URI[dm500hdv2.md5sum] = "c0413bfe6c03efc5fa1825b6ad8ac7bd"
SRC_URI[dm500hdv2.sha256sum] = "005b9e99566fdee4d76ec1532273dc3e29a14b723d0bf6108228988e2a30d013"

FILES_${PN} = "/boot /usr/share /etc/init.d"

do_install() {
    ${@bb.utils.contains("MACHINE_FEATURES", "dreamboxv1", "install -d ${D}/boot", "", d)}
    ${@bb.utils.contains("MACHINE_FEATURES", "dreamboxv1", "install -m 0755 ${S}/dreambox-bootlogo_${BINARY_VERSION}_${PRECOMPILED_ARCH}/bootlogo-${PRECOMPILED_ARCH}.elf.gz ${D}/boot/; install -m 0755 ${S}/splash1280.jpg ${D}/boot/bootlogo-${PRECOMPILED_ARCH}.jpg", "", d)}
    install -d ${D}/usr/share
    install -m 0644 bootlogo.mvi ${D}/usr/share/bootlogo.mvi
    ln -sf /usr/share/bootlogo.mvi ${D}/usr/share/backdrop.mvi
    install -d ${D}/usr/share/enigma2
    install -m 0644 radio.mvi ${D}/usr/share/enigma2/radio.mvi
    install -d ${D}/${sysconfdir}/init.d
    install -m 0755 bootlogo.sh ${D}/${sysconfdir}/init.d/bootlogo
    ${@bb.utils.contains("MACHINE_FEATURES", "gigabluelcd400", "install -m 0644 lcdwaitkey400.bin ${D}/usr/share/lcdwaitkey.bin" , "", d)}
    ${@bb.utils.contains("MACHINE_FEATURES", "gigabluelcd400", "install -m 0644 lcdwarning400.bin ${D}/usr/share/lcdwarning.bin" , "", d)}
    ${@bb.utils.contains("MACHINE_FEATURES", "gigabluelcd220", "install -m 0644 lcdwaitkey220.bin ${D}/usr/share/lcdwaitkey.bin" , "", d)}
    ${@bb.utils.contains("MACHINE_FEATURES", "gigabluelcd220", "install -m 0644 lcdwarning220.bin ${D}/usr/share/lcdwarning.bin" , "", d)}
}

do_install_append_7100s() {
    install -d ${D}/usr/share
    install -m 0644 ${WORKDIR}/7100s/lcdwaitkey220.bin ${D}/usr/share/lcdwaitkey.bin
    install -m 0644 ${WORKDIR}/7100s/lcdwarning220.bin ${D}/usr/share/lcdwarning.bin
    install -m 0644 ${WORKDIR}/7100s/lcdcomplete220.bin ${D}/usr/share/lcdcomplete.bin
}

do_install_append_7210s() {
    install -d ${D}/usr/share
    install -m 0644 ${WORKDIR}/7100s/lcdwaitkey220.bin ${D}/usr/share/lcdwaitkey.bin
    install -m 0644 ${WORKDIR}/7100s/lcdwarning220.bin ${D}/usr/share/lcdwarning.bin
    install -m 0644 ${WORKDIR}/7100s/lcdcomplete220.bin ${D}/usr/share/lcdcomplete.bin
}

do_install_append_7105s() {
    install -d ${D}/usr/share
    install -m 0644 ${WORKDIR}/7100s/lcdwaitkey220.bin ${D}/usr/share/lcdwaitkey.bin
    install -m 0644 ${WORKDIR}/7100s/lcdwarning220.bin ${D}/usr/share/lcdwarning.bin
    install -m 0644 ${WORKDIR}/7100s/lcdcomplete220.bin ${D}/usr/share/lcdcomplete.bin
}

do_install_append_7215s() {
    install -d ${D}/usr/share
    install -m 0644 ${WORKDIR}/7100s/lcdwaitkey220.bin ${D}/usr/share/lcdwaitkey.bin
    install -m 0644 ${WORKDIR}/7100s/lcdwarning220.bin ${D}/usr/share/lcdwarning.bin
    install -m 0644 ${WORKDIR}/7100s/lcdcomplete220.bin ${D}/usr/share/lcdcomplete.bin
}

inherit deploy
do_deploy() {
    if [ "${MACHINE}" = "vuduo" ] || [ "${MACHINE}" = "vuduo2" ] || [ "${MACHINE}" = "vuuno" ] || [ "${MACHINE}" = "vusolo" ] || [ "${MACHINE}" = "vusolose" ] || [ "${MACHINE}" = "vuultimo" ] || [ "${MACHINE}" = "vuzero" ] || [ "${MACHINE}" = "vusolo4k" ] || [ "${BRAND_OEM}" = "dags" ]; then
        install -m 0644 splash480.bmp ${DEPLOYDIR}/${BOOTLOGO_FILENAME}
    else
        install -m 0644 splash576.bmp ${DEPLOYDIR}/${BOOTLOGO_FILENAME}
    fi
    if [ -e lcdsplash220.bin ]; then
        install -m 0644 lcdsplash220.bin ${DEPLOYDIR}/lcdsplash220.bin
    fi
    if [ -e lcdsplash400.bin ]; then
        install -m 0644 lcdsplash400.bin ${DEPLOYDIR}/lcdsplash400.bin
    fi
    if [ -e splash1_os1.bmp ]; then
        install -m 0644 splash1_os1.bmp ${DEPLOYDIR}/splash1_os1.bmp
    fi
    if [ -e splash1_os2.bmp ]; then
        install -m 0644 splash1_os2.bmp ${DEPLOYDIR}/splash1_os2.bmp
    fi
    if [ -e splash1_power.bmp ]; then
        install -m 0644 splash1_power.bmp ${DEPLOYDIR}/splash1.bmp
    fi
	if [ -e splash1.bmp ]; then
        install -m 0644 splash1.bmp ${DEPLOYDIR}/splash1.bmp
    fi
    if [ -e splash2.bmp ]; then
        install -m 0644 splash2.bmp ${DEPLOYDIR}/splash2.bmp
    fi
    if [ -e splash3.bmp ]; then
        install -m 0644 splash3.bmp ${DEPLOYDIR}/splash3.bmp
    fi
}

addtask deploy before do_build after do_install

pkg_preinst_${PN}_dreamboxv1() {
	if [ -z "$D" ]
	then
		if mountpoint -q /boot
		then
			mount -o remount,rw,compr=none /boot
		else
			mount -t jffs2 -o rw,compr=none mtd:boot /boot
		fi
	fi
}

pkg_postinst_${PN}_dreamboxv1() {
	if [ -z "$D" ]
	then
		umount /boot
	fi
}

pkg_prerm_${PN}_dreamboxv1() {
	if [ -z "$D" ]
	then
		if mountpoint -q /boot
		then
			mount -o remount,rw,compr=none /boot
		else
			mount -t jffs2 -o rw,compr=none mtd:boot /boot
		fi
	fi
}

pkg_postrm_${PN}_dreamboxv1() {
	if [ -z "$D" ]
	then
		umount /boot
	fi
}

PACKAGE_ARCH := "${MACHINE_ARCH}"
FILES_${PN} = "/boot /usr/share /etc/init.d"
