SUMMARY = "openViX-HD bootlogo init"
SECTION = "base"
PRIORITY = "required"
MAINTAINER = "rossi2000"
PACKAGE_ARCH = "${MACHINEBUILD}"

require conf/license/license-gplv2.inc

DEPENDS += "eplayer4"
RDEPENDS_${PN} += "showiframe openvixhd-bootlogo-vixhd-animated eplayer4"

PV = "2.3"
PR = "r9"

S = "${WORKDIR}"

INITSCRIPT_NAME = "bootlogo"
INITSCRIPT_PARAMS = "start 06 S ."

inherit update-rc.d

SRC_URI = "file://bootlogo.sh ${@base_contains("MACHINE_FEATURES", "bootsplash", "file://splash.bin " , "", d)}"
SRC_URI_append_gb800ue = "file://lcdsplash.bin"
SRC_URI_append_gbquad = "file://lcdsplash.bin"
SRC_URI_append_gb800ueplus = "file://lcdsplash.bin"
SRC_URI_append_gbquadplus = "file://lcdsplash400.bin"
SRC_URI_append_vuduo2 = "file://lcdbootlogo.png file://bootlogo.py"

FILES_${PN} = "/usr/share /etc/init.d"

do_install() {
    install -d ${D}/${sysconfdir}/init.d
    install -m 0755 bootlogo.sh ${D}/${sysconfdir}/init.d/bootlogo
}

do_install_append_vuduo2() {
    install -d ${D}/usr/share
    install -m 0644 lcdbootlogo.png ${D}/usr/share/lcdbootlogo.png
    install -m 0644 bootlogo.py ${D}/${sysconfdir}/init.d/bootlogo.py
}

inherit deploy
do_deploy() {
    TEST=${MACHINEBUILD}
    if [[ ${TEST:0:2} == "tm" ]]; then
        install -m 0644 tm-splash.bmp ${DEPLOYDIR}/${BOOTLOGO_FILENAME}
    elif [[ ${TEST:0:2} == "iq" ]]; then
        install -m 0644 iqon-splash.bmp ${DEPLOYDIR}/${BOOTLOGO_FILENAME}
    elif [ -e splash.bin ]; then
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
