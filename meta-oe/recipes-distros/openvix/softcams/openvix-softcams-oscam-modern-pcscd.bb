require conf/license/license-gplv2.inc
inherit cmake

SUMMARY = "OScam ${PV} Open Source Softcam, with OMNIKEY support."
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

PV = "1.20+svn${SRCPV}"
SRCREV = "1128"
SRC_URI = "svn://www.streamboard.tv/svn/oscam-addons;protocol=http;module=modern;scmdata=keep;rev=${SRCREV}"
PR = "r1"

PACKAGES = "enigma2-plugin-softcams-oscam-modern-pcscd"

PROVIDES += "openvix-softcams-oscam-modern-pcscd"
RPROVIDES_enigma2-plugin-softcams-oscam-modern-pcscd += "openvix-softcams-oscam-modern-pcscd"

DEPENDS = "libusb openssl pcsc-lite"
RDEPENDS_enigma2-plugin-softcams-oscam-modern-pcscd = "pcsc-lite"
RCONFLICTS_enigma2-plugin-softcams-oscam-modern = "oscam oscam-stable oscam-unstable oscam-experimental oscam-util-list-smargo"
RCONFLICTS_enigma2-plugin-softcams-oscam-modern += "enigma2-plugin-softcams-oscam-cs"
RCONFLICTS_enigma2-plugin-softcams-oscam-modern += "enigma2-plugin-softcams-oscam-stable-cs enigma2-plugin-softcams-oscam-unstable-cs enigma2-plugin-softcams-oscam-experimental-cs"
RCONFLICTS_enigma2-plugin-softcams-oscam-modern += "enigma2-plugin-softcams-oscam-stable    enigma2-plugin-softcams-oscam-unstable    enigma2-plugin-softcams-oscam-experimental"
RREPLACES_enigma2-plugin-softcams-oscam-modern = "${RCONFLICTS_${PN}}"

S = "${WORKDIR}/modern"

EXTRA_OECMAKE += "\
    -DOSCAM_SYSTEM_NAME=Tuxbox \
    -DWEBIF=1 \
    -DWITH_STAPI=0 \
    -DHAVE_LIBUSB=1 \
    -DSTATIC_LIBUSB=1 \
    -DWITH_SSL=1 \
    -DHAVE_PCSC=1"

do_install() {
    install -d ${D}/usr/softcams
    install -m 0755 ${WORKDIR}/build/oscam ${D}/usr/softcams
}

FILES_enigma2-plugin-softcams-oscam-modern-pcscd = "/usr"