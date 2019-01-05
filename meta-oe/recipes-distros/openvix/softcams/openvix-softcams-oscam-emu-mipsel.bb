require conf/license/license-gplv2.inc
inherit cmake

SUMMARY = "OScam ${PV} Open Source Softcam"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

PV = "1.20+svn${SRCPV}"
SRCREV = "11445"
SRC_URI = "svn://www.streamboard.tv/svn/oscam;protocol=http;module=trunk;scmdata=keep;rev=${SRCREV}"

PR = "r1"

do_fetch[depends] += "openvix-softcams-oscam-mipsel:do_fetch"

PACKAGES = "enigma2-plugin-softcams-oscam-emu"

PROVIDES += "openvix-softcams-oscam-emu-mipsel"
RPROVIDES_enigma2-plugin-softcams-oscam-emu += "openvix-softcams-oscam-emu-mipsel"

PATCHREV = "27d6bee218ff95710843a664cc27e698f430471c"
PR = "r783"
SRC_URI += "https://raw.githubusercontent.com/oscam-emu/oscam-emu/${PATCHREV}/oscam-emu.patch?${PATCHREV};downloadfilename=oscam-emu.${PATCHREV}.patch;name=emu;striplevel=0"
SRC_URI[emu.md5sum] = "68381e321877c35b2d8bda5305ee1c5d"
SRC_URI[emu.sha256sum] = "67464a7d6996108feb65278908c4ee178e487b4572c6d4df950f36fd691b98c8"

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
