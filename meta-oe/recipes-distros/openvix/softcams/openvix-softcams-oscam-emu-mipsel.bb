require conf/license/license-gplv2.inc
inherit cmake

SUMMARY = "OScam ${PV} Open Source Softcam"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

PV = "1.20+svn${SRCPV}"
SRCREV = "11693"
SRC_URI = "svn://svn.streamboard.tv/oscam;protocol=https;module=trunk;scmdata=keep;rev=${SRCREV};externals=nowarn"
PR = "r1"

do_fetch[depends] += "openvix-softcams-oscam-mipsel:do_fetch"

PACKAGES = "enigma2-plugin-softcams-oscam-emu"

PROVIDES += "openvix-softcams-oscam-emu-mipsel"
RPROVIDES_enigma2-plugin-softcams-oscam-emu += "openvix-softcams-oscam-emu-mipsel"

FILESEXTRAPATHS_prepend := "${THISDIR}/enigma2-plugin-softcams-oscam-emu:"
PATCHREV = "58a7b142f89857f3ef732a44ce20fa290ff64dee"
PR = "r798"
SRC_URI += "https://raw.githubusercontent.com/oscam-emu/oscam-emu/${PATCHREV}/oscam-emu.patch;downloadfilename=oscam-emu.${PATCHREV}.patch;name=emu;striplevel=0"
SRC_URI[emu.md5sum] = "33125f03229e094cd35c3d8f13322143"
SRC_URI[emu.sha256sum] = "98c60e46754d6b8416e8497c52385d3051187f3e900234fb5bdee1477f716b12"

DEPENDS = "libusb openssl"

S = "${WORKDIR}/trunk"

EXTRA_OECMAKE += "\
    -DOSCAM_SYSTEM_NAME=Tuxbox \
    -DWEBIF=1 \
    -DWITH_STAPI=0 \
    -DHAVE_LIBUSB=1 \
    -DSTATIC_LIBUSB=1 \
    -DWITH_SSL=1 \
    -DCLOCKFIX=0 \
    -DHAVE_PCSC=0"

do_install() {
    install -d ${D}/usr/softcams
    install -m 0755 ${WORKDIR}/build/oscam ${D}/usr/softcams/oscam-emu
}

FILES_enigma2-plugin-softcams-oscam-emu = "/usr"
INSANE_SKIP_${PN} = "already-stripped"
