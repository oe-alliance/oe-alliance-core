require conf/license/license-gplv2.inc
inherit cmake

SUMMARY = "OScam ${PV} Open Source Softcam, with OMNIKEY support."
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

FILESEXTRAPATHS_prepend := "${THISDIR}/openvix-softcams-oscam:"

PV = "1.20+svn${SRCPV}"
SRCREV = "11517"
SRC_URI = "svn://svn.streamboard.tv/oscam;protocol=https;module=trunk;scmdata=keep;rev=${SRCREV};externals=nowarn"

PR = "r1"

do_fetch[depends] += "openvix-softcams-oscam-mipsel:do_fetch"

PACKAGES = "enigma2-plugin-softcams-oscam-pcscd"

PROVIDES += "openvix-softcams-oscam-pcscd-mipsel"
RPROVIDES_enigma2-plugin-softcams-oscam-pcscd += "openvix-softcams-oscam-pcscd-mipsel"

DEPENDS = "libusb openssl pcsc-lite"
RDEPENDS_enigma2-plugin-softcams-oscam-pcscd = "pcsc-lite"

S = "${WORKDIR}/trunk"

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
    install -m 0755 ${WORKDIR}/build/oscam ${D}/usr/softcams/oscam-pcsc
}

FILES_enigma2-plugin-softcams-oscam-pcscd = "/usr"
INSANE_SKIP_${PN} = "already-stripped"
