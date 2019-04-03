require conf/license/license-gplv2.inc
inherit cmake

SUMMARY = "OScam ${PV} Open Source Softcam"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

PV = "1.20+svn${SRCPV}"
SRCREV = "11507"
SRC_URI = "svn://www.streamboard.tv/svn/oscam;protocol=http;module=trunk;scmdata=keep;rev=${SRCREV}"

PR = "r1"

do_fetch[depends] += "openvix-softcams-oscam-mipsel:do_fetch"

PACKAGES = "enigma2-plugin-softcams-oscam-emu"

PROVIDES += "openvix-softcams-oscam-emu-mipsel"
RPROVIDES_enigma2-plugin-softcams-oscam-emu += "openvix-softcams-oscam-emu-mipsel"

PATCHREV = "6214b50e0141972b3145b1d8431262445d429d08"
PR = "r793"
SRC_URI += "https://raw.githubusercontent.com/oscam-emu/oscam-emu/${PATCHREV}/oscam-emu.patch?${PATCHREV};downloadfilename=oscam-emu.${PATCHREV}.patch;name=emu;striplevel=0"
SRC_URI[emu.md5sum] = "0cb6abb443661a2e4e579ed9a6ed7cf7"
SRC_URI[emu.sha256sum] = "ce0faef77f548d76148eb0751843113cb6809e47ebd2167b1e9e24b71f24da38"

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
