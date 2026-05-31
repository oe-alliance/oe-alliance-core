MAINTAINER = "oscam-emu"
SUMMARY:${PN} = "OSCam-emu ${PKGV}"
DESCRIPTION:${PN} = "OSCam Open Source Softcam\n \
- with emu support"
LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

require conf/license/license-gplv2.inc
inherit cmake gitpkgv

SRCREV = "${AUTOREV}"
PV = "1.30+git"
PKGV = "1.30+git${GITPKGV}"
PR = "r1"

DEPENDS += "libusb openssl libdvbcsa"
RDEPENDS:${PN} += "libdvbcsa libusb1"

FILESEXTRAPATHS:prepend := "${THISDIR}/cam-oscam-latest:"

SRC_URI += "git://github.com/oscam-mirror/oscam-emu.git;protocol=https;branch=master \
        file://init3 \
        file://oscam.conf.example \
        file://oscam.server.example \
        file://oscam.user.example \
        "
CAMNAME = "oscam-emu"

FILES:${PN} = "/usr/bin/ /usr/script/ /var/volatile/log/${CAMNAME}/ /etc/tuxbox/config/${CAMNAME}/"

PACKAGES = "${PN}"
PACKAGE_ARCH = "${TUNE_PKGARCH}"

LDFLAGS:prepend = "-ldvbcsa "

EXTRA_OECMAKE += "\
    -DOSCAM_SYSTEM_NAME=Tuxbox \
    -DWEBIF=1 \
    -DWEBIF_LIVELOG=1 \
    -DWEBIF_JQUERY=1 \
    -DTOUCH=1 \
    -DLCDSUPPORT=1 \
    -DLEDSUPPORT=1 \
    -DWITH_SSL=1 \
    -DIPV6SUPPORT=1 \
    -DHAVE_PCSC=0 \
    -DWITH_STAPI=0 \
    -DHAVE_LIBUSB=1 \
    -DSTATIC_LIBUSB=0 \
    -DMODULE_CONSTCW=1 \
    -DMODULE_STREAMRELAY=1 \
    -DHAVE_LIBDVBCSA=1 \
"

do_install() {
    install -d ${D}/usr/script
    install -m 0755 ${UNPACKDIR}/init3 ${D}/usr/script/Oscam-emu_cam.sh

    install -d ${D}/usr/bin
    install -m 0755 ${B}/oscam ${D}/usr/bin/${CAMNAME}

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
rm -rf /usr/script/Oscam-emu_cam.sh > /dev/null 2>&1

exit 0
}

do_prepare_recipe_sysroot[noexec] = "1"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
deltask do_populate_sysroot
deltask do_package_qa
