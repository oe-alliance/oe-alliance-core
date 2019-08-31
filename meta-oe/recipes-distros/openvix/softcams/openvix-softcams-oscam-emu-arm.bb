require conf/license/license-gplv2.inc
inherit cmake

SUMMARY = "OScam ${PV} Open Source Softcam"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

PV = "1.20+svn${SRCPV}"
SRCREV = "11546"
SRC_URI = "svn://www.streamboard.tv/svn/oscam;protocol=http;module=trunk;scmdata=keep;rev=${SRCREV};externals=nowarn"
PR = "r1"

PACKAGES = "enigma2-plugin-softcams-oscam-emu"

PROVIDES += "openvix-softcams-oscam-emu-arm"
RPROVIDES_enigma2-plugin-softcams-oscam-emu += "openvix-softcams-oscam-emu-arm"

FILESEXTRAPATHS_prepend := "${THISDIR}/enigma2-plugin-softcams-oscam-emu:"
PATCHREV = "7545f253ec28fca0f5aff686bfe76f3f3f46dc99"
PR = "r797"
SRC_URI += "https://raw.githubusercontent.com/oscam-emu/oscam-emu/${PATCHREV}/oscam-emu.patch?${PATCHREV};downloadfilename=oscam-emu.${PATCHREV}.patch;name=emu;striplevel=0"
SRC_URI[emu.md5sum] = "0a6efdc153daeda58c44e7e177f4d77b"
SRC_URI[emu.sha256sum] = "4d345b2ea7483775dd3c92e2c7ae63501f7f3c7c4a8cec717f49955a0ae2ea1d"

DEPENDS = "libusb openssl"

S = "${WORKDIR}/trunk"

EXTRA_OECMAKE += "\
    -DOSCAM_SYSTEM_NAME=FriendlyARM \
    -DWEBIF=1 \
    -DWITH_STAPI=0 \
    -DHAVE_LIBUSB=1 \
    -DSTATIC_LIBUSB=1 \
    -DWITH_SSL=1 \
    -DCLOCKFIX=0 \
    -DMODULE_CONSTCW=1 \
    -DHAVE_PCSC=0"

do_install() {
    install -d ${D}/usr/softcams
    install -m 0755 ${WORKDIR}/build/oscam ${D}/usr/softcams/oscam-emu
}

FILES_enigma2-plugin-softcams-oscam-emu = "/usr"
INSANE_SKIP_${PN} = "already-stripped"
