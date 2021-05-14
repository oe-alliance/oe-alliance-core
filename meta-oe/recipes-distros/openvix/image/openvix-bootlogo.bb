SUMMARY = "OpenViX Bootlogo"
MAINTAINER = "OpenViX"
SECTION = "base"
PRIORITY = "required"
PACKAGE_ARCH = "${MACHINEBUILD}"

require conf/license/license-gplv2.inc

RDEPENDS_${PN} += "showiframe"

PV = "${IMAGE_VERSION}"
PR = "r21"

S = "${WORKDIR}"

INITSCRIPT_NAME = "bootlogo"
INITSCRIPT_PARAMS = "start 06 S ."
INITSCRIPT_PARAMS_vuduo2 = "start 70 S ."
INITSCRIPT_PARAMS_vusolo2 = "start 70 S ."
INITSCRIPT_PARAMS_vusolose = "start 70 S ."
INITSCRIPT_PARAMS_vusolo4k = "start 70 S ."
INITSCRIPT_PARAMS_vuuno4k = "start 70 S ."
INITSCRIPT_PARAMS_vuuno4kse = "start 70 S ."
INITSCRIPT_PARAMS_vuultimo4k = "start 70 S ."
INITSCRIPT_PARAMS_vuzero4k = "start 70 S ."

inherit update-rc.d

SRC_URI = "file://bootlogo.mvi file://backdrop.mvi file://radio.mvi file://bootlogo.sh ${@bb.utils.contains("MACHINE_FEATURES", "bootsplash", "file://splash.bin " , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "gigabluelcd220", "file://lcdsplash220.bin file://lcdwaitkey220.bin file://lcdwarning220.bin" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "gigabluelcd400", "file://lcdsplash400.bin file://lcdwaitkey400.bin file://lcdwarning400.bin" , "", d)} \
"

SRC_URI_append_vuduo2 = "file://lcdbootlogo.png file://bootlogo.py"
SRC_URI_append_dags7335 = "file://tm-splash.bmp file://iqon-splash.bmp file://splash1.bmp file://splash2.bmp file://splash3.bmp"
SRC_URI_append_dags7356 = "file://tm-splash.bmp file://iqon-splash.bmp file://splash1.bmp file://splash2.bmp file://splash3.bmp"
SRC_URI_append_dags7362 = "file://tm-splash.bmp file://iqon-splash.bmp file://splash1_power.bmp file://splash2.bmp file://splash3.bmp"
SRC_URI_append_dags7252 = "file://tm-splash.bmp file://splash1.bmp file://splash2.bmp file://splash3.bmp"
SRC_URI_append_7210s = "file://lcdsplash220.bin file://lcdwaitkey220.bin file://lcdwarning220.bin file://lcdcomplete220.bin"
SRC_URI_append_h9 = "file://logo.img"
SRC_URI_append_h9se = "file://logo.img"
SRC_URI_append_h9combo = "file://logo.img"
SRC_URI_append_i55plus = "file://logo.img"
SRC_URI_append_hd60 = "file://logo.img"
SRC_URI_append_hd61 = "file://logo.img"
SRC_URI_append_cc1 = "file://logo.img"
SRC_URI_append_sf8008 = "file://logo.img"
SRC_URI_append_sf8008m = "file://logo.img"
SRC_URI_append_ustym4kpro = "file://logo.img"
SRC_URI_append_v8plus = "file://logo.img"
SRC_URI_append_multibox = "file://logo.img"
SRC_URI_append_gbmv200 = "file://logo.img"
SRC_URI_append_beyonwizv2 = "file://logo.img"
SRC_URI_append_viper4k = "file://logo.img"


FILES_${PN} = "/usr/share /usr/share/enigma2 /etc/init.d"

do_install() {
    install -d ${D}/${sysconfdir}/init.d
    install -m 0755 bootlogo.sh ${D}/${sysconfdir}/init.d/bootlogo
    install -d ${D}/usr/share
    install -m 0644 bootlogo.mvi ${D}/usr/share/bootlogo.mvi
    install -m 0644 backdrop.mvi ${D}/usr/share/backdrop.mvi
    ${@bb.utils.contains("MACHINE_FEATURES", "gigabluelcd400", "install -m 0644 lcdwaitkey400.bin ${D}/usr/share/lcdwaitkey.bin" , "", d)}
    ${@bb.utils.contains("MACHINE_FEATURES", "gigabluelcd400", "install -m 0644 lcdwarning400.bin ${D}/usr/share/lcdwarning.bin" , "", d)}
    ${@bb.utils.contains("MACHINE_FEATURES", "gigabluelcd220", "install -m 0644 lcdwaitkey220.bin ${D}/usr/share/lcdwaitkey.bin" , "", d)}
    ${@bb.utils.contains("MACHINE_FEATURES", "gigabluelcd220", "install -m 0644 lcdwarning220.bin ${D}/usr/share/lcdwarning.bin" , "", d)}
    install -d ${D}/usr/share/enigma2
    install -m 0644 radio.mvi ${D}/usr/share/enigma2/radio.mvi
}

do_install_append_vuduo2() {
    install -d ${D}/usr/share
    install -m 0644 lcdbootlogo.png ${D}/usr/share/lcdbootlogo.png
    install -m 0644 bootlogo.py ${D}/${sysconfdir}/init.d/bootlogo.py
}

inherit deploy
do_deploy() {
    TEST=${MACHINEBUILD}
    if [ "$( echo $TEST | awk '{ string=substr($0, 1, 2); print string; }' )" = "tm" ]; then
        install -m 0644 tm-splash.bmp ${DEPLOYDIR}/${BOOTLOGO_FILENAME}
    elif [ "$( echo $TEST | awk '{ string=substr($0, 1, 2); print string; }' )" = "iq" ]; then
        install -m 0644 iqon-splash.bmp ${DEPLOYDIR}/${BOOTLOGO_FILENAME}
    elif [ -e splash.bin ]; then
        install -m 0644 splash.bin ${DEPLOYDIR}/${BOOTLOGO_FILENAME}
    fi
    if [ -e lcdsplash220.bin ]; then
        install -m 0644 lcdsplash220.bin ${DEPLOYDIR}/lcdsplash220.bin
    fi
    if [ -e lcdsplash400.bin ]; then
        install -m 0644 lcdsplash400.bin ${DEPLOYDIR}/lcdsplash400.bin
    fi
    if [ -e splash1.bmp ]; then
        install -m 0644 splash1.bmp ${DEPLOYDIR}/splash1.bmp
    fi
    if [ -e splash1_power.bmp ]; then
        install -m 0644 splash1_power.bmp ${DEPLOYDIR}/splash1_power.bmp
    fi
    if [ -e splash2.bmp ]; then
        install -m 0644 splash2.bmp ${DEPLOYDIR}/splash2.bmp
    fi
    if [ -e splash3.bmp ]; then
        install -m 0644 splash3.bmp ${DEPLOYDIR}/splash3.bmp
    fi
    if [ -e logo.img ]; then
        install -m 0644 logo.img ${DEPLOYDIR}/logo-${DISTRO_NAME}.img
    fi
}

addtask deploy before do_build after do_install
