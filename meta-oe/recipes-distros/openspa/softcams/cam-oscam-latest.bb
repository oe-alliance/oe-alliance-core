SUMMARY:${PN} = "OSCam-latest ${PKGV}"
DESCRIPTION:${PN} = "OSCam Open Source Softcam\n \
- latest trunk"
LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

require conf/license/license-gplv2.inc
inherit cmake gitpkgv

SRCREV = "${AUTOREV}"
PV = "1.30+git"
PKGV = "${PV}"
PR = "r1"

DEPENDS += "libusb openssl libdvbcsa"
RDEPENDS:${PN} += "libdvbcsa libusb1"

FILESEXTRAPATHS:prepend := "${THISDIR}/cam-oscam-latest:"

SRC_URI += "git://git.streamboard.tv/common/oscam.git;protocol=https;branch=master \
        file://init \
        file://oscam.conf.example \
        file://oscam.server.example \
        file://oscam.user.example \
        "
CAMNAME = "oscam-latest"

S = "${WORKDIR}/git"
UNPACKDIR = "${WORKDIR}/sources"

FILES:${PN} = "/usr/bin/ /usr/script/ /var/volatile/log/${CAMNAME}/ /etc/tuxbox/config/${CAMNAME}/"

PACKAGE_ARCH = "${TUNE_PKGARCH}"

LDFLAGS:prepend = "-ldvbcsa "

EXTRA_OECMAKE += "\
    -DOSCAM_SYSTEM_NAME=Tuxbox \
    -DWEBIF=1 \
    -DWITH_STAPI=0 \
    -DHAVE_LIBUSB=1 \
    -DSTATIC_LIBUSB=0 \
    -DWITH_SSL=1 \
    -DIPV6SUPPORT=1 \
    -DHAVE_PCSC=0 \
    -DCS_CACHEEX=1 \
    -DCS_CACHEEX_AIO=1 \
    -DCS_ANTICASC=1 \
    -DCW_CYCLE_CHECK=1 \
    -DMODULE_CONSTCW=1 \
    -DLCDSUPPORT=1 \
    -DMODULE_SCAM=1 \
    -DMODULE_STREAMRELAY=1 \
    -DHAVE_LIBDVBCSA=1 \
"

do_install() {
    install -d ${D}/usr/script
    install -m 0755 ${UNPACKDIR}/init ${D}/usr/script/Oscam-latest_cam.sh
    install -d ${D}/usr/bin
    install -m 0755 ${WORKDIR}/build/oscam ${D}/usr/bin/${CAMNAME}
    install -m 0755 ${WORKDIR}/build/utils/list_smargo ${D}/usr/bin/list_smargo
    install -d ${D}/etc/tuxbox/config/${CAMNAME}
    install -m 0644 ${UNPACKDIR}/oscam.conf.example ${D}/etc/tuxbox/config/${CAMNAME}
    install -m 0644 ${UNPACKDIR}/oscam.server.example ${D}/etc/tuxbox/config/${CAMNAME}
    install -m 0644 ${UNPACKDIR}/oscam.user.example ${D}/etc/tuxbox/config/${CAMNAME}
    install -d ${D}/var/volatile/log/${CAMNAME}/
}

pkg_postinst:${PN}() {
#!/bin/sh
echo "*******************************************************"
echo "Para nuevas versiones y dudas estamos en:"
echo "           http://openspa.info/          "
echo "*******************************************************"
exit 0
}

pkg_prerm:${PN}() {
#!/bin/sh

exit 0
}

pkg_postrm:${PN}() {
#!/bin/sh

rm -rf /etc/tuxbox/config/${CAMNAME}/oscam.conf.example > /dev/null 2>&1
rm -rf /etc/tuxbox/config/${CAMNAME}/oscam.server.example > /dev/null 2>&1
rm -rf /etc/tuxbox/config/${CAMNAME}/oscam.user.example > /dev/null 2>&1
rm -rf /usr/bin/${CAMNAME} > /dev/null 2>&1
rm -rf /usr/script/Oscam-latest_cam.sh > /dev/null 2>&1

exit 0
}
