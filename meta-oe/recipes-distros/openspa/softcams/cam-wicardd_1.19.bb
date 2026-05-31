SUMMARY:${PN} = "wicardd ${PKGV}"
DESCRIPTION:${PN} = "An outdated binary only softcam"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PR = "r0"

FILESEXTRAPATHS:prepend := "${THISDIR}/cam-wicardd:"

SRC_URI = " \
            file://wicardd_versions \
            file://wicardd.conf.info \
            file://init.wicardd \
"

require softcam-binary-32bit.inc

CAMNAME = "wicardd_1.19"

S = "${UNPACKDIR}"

FILES:${PN} = "/usr/bin/ /usr/script/ /etc/tuxbox/config/wicardd/"

PACKAGES = "${PN}"
PACKAGE_ARCH = "${TUNE_PKGARCH}"

do_install() {
    install -d ${D}/usr/script
    install -m 0755 ${S}/init.wicardd ${D}/usr/script/${CAMNAME}_cam.sh
    install -d ${D}/${bindir}
    install -d ${D}/usr/bin
    install -m 0755 ${S}/wicardd_versions/${CAMNAME}_${TARGET_ARCH} ${D}/usr/bin/wicardd
    install -d ${D}/etc/tuxbox/config/wicardd
    install -m 0755 ${S}/wicardd.conf.info ${D}/etc/tuxbox/config/wicardd
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

rm -rf /etc/tuxbox/config/wicardd/wicardd.cfg.info > /dev/null 2>&1
rm -rf /usr/bin/wicardd > /dev/null 2>&1
rm -rf /usr/script/${CAMNAME}_cam.sh > /dev/null 2>&1

exit 0
}

do_prepare_recipe_sysroot[noexec] = "1"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
deltask do_populate_sysroot
deltask do_package_qa
