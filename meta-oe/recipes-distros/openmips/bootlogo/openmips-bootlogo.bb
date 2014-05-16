SUMMARY = "openMips bootlogo"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "openMips"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

RDEPENDS_${PN} += "showiframe"

PV = "4.0"
PR = "r6"

S = "${WORKDIR}"

INITSCRIPT_NAME = "bootlogo"
INITSCRIPT_PARAMS = "start 06 S ."

inherit update-rc.d

SRC_URI = "file://bootlogo.mvi file://bootlogo.sh ${@base_contains("MACHINE_FEATURES", "bootsplash", "file://splash.bin" , "", d)} "
SRC_URI_append_gb800ue = "file://lcdsplash.bin file://lcdwaitkey.bin file://lcdwarning.bin"
SRC_URI_append_gbquad = "file://lcdsplash.bin file://lcdwaitkey.bin file://lcdwarning.bin"
SRC_URI_append_gbquadplus = "file://lcdsplash400.bin file://lcdwaitkey400.bin file://lcdwarning400.bin"
SRC_URI_append_gb800ueplus = "file://lcdsplash.bin file://lcdwaitkey.bin file://lcdwarning.bin"

FILES_${PN} = "/boot /usr/share /etc/init.d"

do_install() {
    install -d ${D}/usr/share
    install -m 0644 bootlogo.mvi ${D}/usr/share/bootlogo.mvi
    ln -sf /usr/share/bootlogo.mvi ${D}/usr/share/backdrop.mvi
    install -d ${D}/usr/share/enigma2
    install -d ${D}/${sysconfdir}/init.d
    install -m 0755 ${S}/bootlogo.sh ${D}/${sysconfdir}/init.d/bootlogo
}

do_install_append_gb800ue() {
    install -d ${D}/usr/share
    install -m 0644 lcdwaitkey.bin ${D}/usr/share/lcdwaitkey.bin
    install -m 0644 lcdwarning.bin ${D}/usr/share/lcdwarning.bin
}

do_install_append_gbquad() {
    install -d ${D}/usr/share
    install -m 0644 lcdwaitkey.bin ${D}/usr/share/lcdwaitkey.bin
    install -m 0644 lcdwarning.bin ${D}/usr/share/lcdwarning.bin	
}

do_install_append_gb800ueplus() {
    install -d ${D}/usr/share
    install -m 0644 lcdwaitkey.bin ${D}/usr/share/lcdwaitkey.bin
    install -m 0644 lcdwarning.bin ${D}/usr/share/lcdwarning.bin	
}

do_install_append_gbquadplus() {
    install -d ${D}/usr/share
    install -m 0644 lcdwaitkey400.bin ${D}/usr/share/lcdwaitkey.bin
    install -m 0644 lcdwarning400.bin ${D}/usr/share/lcdwarning.bin	
}

inherit deploy
do_deploy() {
    if [ -e splash.bin ]; then
        install -m 0644 splash.bin ${DEPLOYDIR}/${BOOTLOGO_FILENAME}
    fi
    if [ -e lcdsplash.bin ]; then
        install -m 0644 lcdsplash.bin ${DEPLOYDIR}/lcdsplash.bin
    fi

    if [ -e lcdsplash400.bin ]; then
        install -m 0644 lcdsplash400.bin ${DEPLOYDIR}/lcdsplash.bin
    fi
}

addtask deploy before do_build after do_install
