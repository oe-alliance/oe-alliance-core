SUMMARY = "openeight bootlogo"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "openeight"

require conf/license/license-gplv2.inc

RDEPENDS_${PN} += "showiframe"

PV = "1.0"
PR = "r5"

S = "${WORKDIR}/"

INITSCRIPT_NAME = "bootlogo"
INITSCRIPT_PARAMS = "start 06 S ."

inherit update-rc.d

SRC_URI = " file://bootlogo.mvi ${@bb.utils.contains("MACHINE_FEATURES", "bootsplash", "file://splash.bin" , "", d)} file://bootlogo.sh"

SRC_URI_append_7210s = " file://lcdsplash220.bin file://lcdwaitkey220.bin file://lcdwarning220.bin"
SRC_URI_append_sf8008 = " file://logo.img"
SRC_URI_append_sf8008m = " file://logo.img"

FILES_${PN} = "/boot /usr/share /etc/init.d"

do_install() {
    install -d ${D}/usr/share
    install -m 0644 bootlogo.mvi ${D}/usr/share/bootlogo.mvi
    ln -sf /usr/share/bootlogo.mvi ${D}/usr/share/backdrop.mvi
    install -d ${D}/${sysconfdir}/init.d
    install -m 0755 ${S}/bootlogo.sh ${D}/${sysconfdir}/init.d/bootlogo
}

do_install_append_7210s() {
    install -d ${D}/usr/share
    install -m 0644 ${WORKDIR}/lcdsplash220.bin ${D}/usr/share/lcdsplash.bin
    install -m 0644 ${WORKDIR}/lcdwaitkey220.bin ${D}/usr/share/lcdwaitkey.bin
    install -m 0644 ${WORKDIR}/lcdwarning220.bin ${D}/usr/share/lcdwarning.bin
}

inherit deploy
do_deploy() {
    if [ -e splash.bin ]; then
        install -m 0644 splash.bin ${DEPLOYDIR}/${BOOTLOGO_FILENAME}
    fi
    if [ -e lcdsplash220.bin ]; then
        install -m 0644 lcdsplash220.bin ${DEPLOYDIR}/lcdsplash220.bin
    fi
    if [ -e logo.img ]; then
        install -m 0644 logo.img ${DEPLOYDIR}/logo-${DISTRO_NAME}.img
    fi
}

addtask deploy before do_build after do_install


PACKAGE_ARCH := "${MACHINE_ARCH}"
FILES_${PN} = "/boot /usr/share /etc/init.d"

