require conf/license/license-gplv2.inc
inherit cmake
inherit gitpkgv

SUMMARY = "OScam ${PV} Open Source Softcam, with OMNIKEY support."
LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

PV = "1.30+git"
SRCREV = "${AUTOREV}"
SRC_URI += "git://git.streamboard.tv/common/oscam.git;protocol=https;branch=master"

PACKAGES = "enigma2-plugin-softcams-oscam-pcscd-latest"

PROVIDES += "openvix-softcams-oscam-pcscd-latest-arm"
RPROVIDES:enigma2-plugin-softcams-oscam-pcscd-latest += "openvix-softcams-oscam-pcscd-latest-arm"

DEPENDS = "libusb openssl pcsc-lite"
RDEPENDS:enigma2-plugin-softcams-oscam-pcscd-latest = "pcsc-lite"

S = "${WORKDIR}/git"

EXTRA_OECMAKE += "\
    -DOSCAM_SYSTEM_NAME=FriendlyARM \
    -DWEBIF=1 \
    -DWITH_STAPI=0 \
    -DHAVE_LIBUSB=1 \
    -DSTATIC_LIBUSB=1 \
    -DWITH_SSL=1 \
    -DCLOCKFIX=0 \
    -DHAVE_PCSC=1"

do_install() {
    install -d ${D}/usr/softcams
    install -m 0755 ${S}/build/oscam ${D}/usr/softcams/oscam-pcsc-latest
}

FILES:enigma2-plugin-softcams-oscam-pcscd-latest= "/usr"
INSANE_SKIP:${PN} = "already-stripped"
