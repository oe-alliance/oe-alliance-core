require conf/license/license-gplv2.inc
inherit cmake
inherit gitpkgv

SUMMARY = "OScam ${PV} Open Source Softcam, with OMNIKEY support."
LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

FILESEXTRAPATHS:prepend := "${THISDIR}/openvix-softcams-oscam:"

PV = "1.20+11517"
SRCREV = "a2ee0528f1de527748aaf7a79982ed112ffa3183"

SRC_URI = "git://repo.or.cz/oscam.git;protocol=git"

PR = "r1"

PACKAGES = "enigma2-plugin-softcams-oscam-pcscd"

PROVIDES += "openvix-softcams-oscam-pcscd-mipsel"
RPROVIDES:enigma2-plugin-softcams-oscam-pcscd += "openvix-softcams-oscam-pcscd-mipsel"

DEPENDS = "libusb openssl pcsc-lite"
RDEPENDS:enigma2-plugin-softcams-oscam-pcscd = "pcsc-lite"

S = "${WORKDIR}/git"

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

FILES:enigma2-plugin-softcams-oscam-pcscd = "/usr"
INSANE_SKIP:${PN} = "already-stripped"
