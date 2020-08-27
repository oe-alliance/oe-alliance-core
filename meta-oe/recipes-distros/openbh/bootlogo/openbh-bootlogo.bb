SUMMARY = "OpenBH Bootlogo"
MAINTAINER = "BlackHole Team"
SECTION = "base"
PRIORITY = "required"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

RDEPENDS_${PN} += "showiframe"

PV = "${IMAGE_VERSION}"
PR = "r18"

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
INITSCRIPT_PARAMS_vuduo4k = "start 70 S ."

inherit update-rc.d

SRC_URI = "file://bootlogo.mvi file://backdrop.mvi file://radio.mvi file://bootlogo.sh file://splash576.bmp file://splash480.bmp \
"

SRC_URI_append_vuduo2 = "file://lcdbootlogo.png file://bootlogo.py"

FILES_${PN} = "/boot /usr/share /etc/init.d"

do_install() {
    install -d ${D}/usr/share
    install -m 0644 bootlogo.mvi ${D}/usr/share/bootlogo.mvi
    install -m 0644 backdrop.mvi ${D}/usr/share/backdrop.mvi
    install -d ${D}/usr/share/enigma2
    install -m 0644 radio.mvi ${D}/usr/share/enigma2/radio.mvi
    install -d ${D}/${sysconfdir}/init.d
    install -m 0755 bootlogo.sh ${D}/${sysconfdir}/init.d/bootlogo
}

do_install_append_vuduo2() {
    install -m 0644 lcdbootlogo.png ${D}/usr/share/lcdbootlogo.png
    install -m 0644 bootlogo.py ${D}/${sysconfdir}/init.d/bootlogo.py
}

inherit deploy
do_deploy() {
    if [ "${MACHINE}" = "vuduo" ] || [ "${MACHINE}" = "vuduo2" ] || [ "${MACHINE}" = "vuuno" ] || [ "${MACHINE}" = "vusolo" ] || [ "${MACHINE}" = "vusolose" ] || [ "${MACHINE}" = "vuultimo" ] || [ "${MACHINE}" = "vuzero" ] || [ "${MACHINE}" = "vuzero4k" ] || [ "${MACHINE}" = "vusolo4k" ] || [ "${MACHINE}" = "vuuno4k" ] || [ "${MACHINE}" = "vuuno4kse" ] || [ "${MACHINE}" = "vuultimo4k" ] || [ "${MACHINE}" = "vuduok" ]; then
        install -m 0644 splash480.bmp ${DEPLOYDIR}/${BOOTLOGO_FILENAME}
    else
        install -m 0644 splash576.bmp ${DEPLOYDIR}/${BOOTLOGO_FILENAME}
    fi
}

addtask deploy before do_build after do_install
