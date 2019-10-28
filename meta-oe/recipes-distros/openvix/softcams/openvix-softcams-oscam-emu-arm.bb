require conf/license/license-gplv2.inc
inherit cmake

SUMMARY = "OScam ${PV} Open Source Softcam"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

PV = "1.20+svn${SRCPV}"
SRCREV = "11561"
SRC_URI = "svn://www.streamboard.tv/svn/oscam;protocol=http;module=trunk;scmdata=keep;rev=${SRCREV}"
PR = "r1"

PACKAGES = "enigma2-plugin-softcams-oscam-emu"

PROVIDES += "openvix-softcams-oscam-emu-arm"
RPROVIDES_enigma2-plugin-softcams-oscam-emu += "openvix-softcams-oscam-emu-arm"

FILESEXTRAPATHS_prepend := "${THISDIR}/enigma2-plugin-softcams-oscam-emu:"
PATCHREV = "9a201e0013b3ca47c91c1d0eed6b8658ab0be826"
PR = "r798"
SRC_URI += "https://raw.githubusercontent.com/oscam-emu/oscam-emu/${PATCHREV}/oscam-emu.patch?${PATCHREV};downloadfilename=oscam-emu.${PATCHREV}.patch;name=emu;striplevel=0"
SRC_URI[emu.md5sum] = "591c0652dd315903e6da20cad512312b"
SRC_URI[emu.sha256sum] = "d111e4d59e11f6e6298362efd842f018cd0604f7b33b1e8434fd63d9a5c32aa0"

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
