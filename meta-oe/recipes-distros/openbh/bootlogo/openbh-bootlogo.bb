SUMMARY = "OpenBH Bootlogo"
MAINTAINER = "BlackHole Team"
SECTION = "base"
PRIORITY = "required"
PACKAGE_ARCH = "${MACHINEBUILD}"

require conf/license/license-gplv2.inc

RDEPENDS_${PN} += "showiframe"

PV = "${IMAGE_VERSION}"
PR = "r11"

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

SRC_URI = "file://bootlogo.mvi file://radio.mvi file://bootlogo.sh file://splash576.bmp file://splash480.bmp \
"

SRC_URI_append_vuduo2 = "file://lcdbootlogo.png file://bootlogo.py"

FILES_${PN} = "/usr/share /usr/share/enigma2 /etc/init.d"

do_install() {
    install -d ${D}/${sysconfdir}/init.d
    install -m 0755 bootlogo.sh ${D}/${sysconfdir}/init.d/bootlogo
    install -d ${D}/usr/share
    install -m 0644 bootlogo.mvi ${D}/usr/share/bootlogo.mvi
    install -d ${D}/usr/share/enigma2
    install -m 0644 radio.mvi ${D}/usr/share/enigma2/radio.mvi
}

do_install_append_vuduo2() {
    install -m 0644 lcdbootlogo.png ${D}/usr/share/lcdbootlogo.png
    install -m 0644 bootlogo.py ${D}/${sysconfdir}/init.d/bootlogo.py
}