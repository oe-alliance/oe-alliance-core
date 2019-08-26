SUMMARY = "teamBlue bootlogo"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "teamBlue"
PACKAGE_ARCH = "${MACHINEBUILD}"

require conf/license/license-gplv2.inc

RDEPENDS_${PN} += "showiframe"

PV = "${IMAGE_VERSION}"
PR = "r4"

S = "${WORKDIR}"

INITSCRIPT_NAME = "bootlogo"
INITSCRIPT_PARAMS = "start 06 S ."
INITSCRIPT_PARAMS_gb7252 = "start 70 S ."

inherit update-rc.d

SRC_URI = "file://bootlogo.mvi file://bootlogo.sh \
    ${@bb.utils.contains("MACHINE_FEATURES", "bootsplash", "file://splash.bin" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "gigabluelcd220", "file://lcdsplash220.bin file://lcdwaitkey220.bin file://lcdwarning220.bin" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "gigabluelcd400", "file://lcdsplash400.bin file://lcdwaitkey400.bin file://lcdwarning400.bin" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "gigabluemipsbootvideo", "file://bootvideo.mp4 file://bootvideo-mips" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "gigabluearmbootvideo", "file://bootvideo.mp4 file://bootvideo-arm" , "", d)} \
"

SRC_URI_append_gbmv200 = "file://logo.img"

FILES_${PN} = "/boot ${datadir} ${bindir} ${sysconfdir}/init.d"

INSANE_SKIP_${PN} = "already-stripped"

do_install() {
    install -d ${D}${datadir}
    install -m 0644 bootlogo.mvi ${D}${datadir}/bootlogo.mvi
    ln -sf ${datadir}/bootlogo.mvi ${D}${datadir}/backdrop.mvi

    ${@bb.utils.contains("MACHINE_FEATURES", "gigabluemipsbootvideo", "install -m 0644 bootvideo.mp4 ${D}${datadir}/bootvideo.mp4" , "", d)}
    ${@bb.utils.contains("MACHINE_FEATURES", "gigabluemipsbootvideo", "install -d ${D}${bindir}" , "", d)}
    ${@bb.utils.contains("MACHINE_FEATURES", "gigabluemipsbootvideo", "install -m 0755 ${S}/bootvideo-mips ${D}${bindir}/bootvideo" , "", d)}

    ${@bb.utils.contains("MACHINE_FEATURES", "gigabluearmbootvideo", "install -m 0644 bootvideo.mp4 ${D}${datadir}/bootvideo.mp4" , "", d)}
    ${@bb.utils.contains("MACHINE_FEATURES", "gigabluearmbootvideo", "install -d ${D}${bindir}" , "", d)}
    ${@bb.utils.contains("MACHINE_FEATURES", "gigabluearmbootvideo", "install -m 0755 ${S}/bootvideo-arm ${D}${bindir}/bootvideo" , "", d)}

    install -d ${D}${datadir}/enigma2
    install -d ${D}/${sysconfdir}/init.d
    install -m 0755 ${S}/bootlogo.sh ${D}/${sysconfdir}/init.d/bootlogo
    ${@bb.utils.contains("MACHINE_FEATURES", "gigabluelcd400", "install -m 0644 lcdwaitkey400.bin ${D}${datadir}/lcdwaitkey.bin" , "", d)}
    ${@bb.utils.contains("MACHINE_FEATURES", "gigabluelcd400", "install -m 0644 lcdwarning400.bin ${D}${datadir}/lcdwarning.bin" , "", d)}
    ${@bb.utils.contains("MACHINE_FEATURES", "gigabluelcd220", "install -m 0644 lcdwaitkey220.bin ${D}${datadir}/lcdwaitkey.bin" , "", d)}
    ${@bb.utils.contains("MACHINE_FEATURES", "gigabluelcd220", "install -m 0644 lcdwarning220.bin ${D}${datadir}/lcdwarning.bin" , "", d)}
}

inherit deploy
do_deploy() {
    if [ -e splash.bin ]; then
        install -m 0644 splash.bin ${DEPLOYDIR}/${BOOTLOGO_FILENAME}
    fi
    if [ -e lcdsplash220.bin ]; then
        install -m 0644 lcdsplash220.bin ${DEPLOYDIR}/lcdsplash220.bin
    fi

    if [ -e lcdsplash400.bin ]; then
        install -m 0644 lcdsplash400.bin ${DEPLOYDIR}/lcdsplash400.bin
    fi
    if [ -e logo.img ]; then
        install -m 0644 logo.img ${DEPLOYDIR}/logo-${DISTRO_NAME}.img
    fi
}

do_package_qa[noexec] = "1"

addtask deploy before do_build after do_install
