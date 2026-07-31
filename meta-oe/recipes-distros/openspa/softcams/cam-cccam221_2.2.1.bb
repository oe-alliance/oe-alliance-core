SUMMARY:${PN} = "CCcam ${PKGV}"
DESCRIPTION:${PN} = "An outdated binary only softcam"
require conf/license/license-close.inc

PR = "r0"

RDEPENDS:${PN} = "glibc libstdc++"
RDEPENDS:${PN}_aarch64 = "lib32-glibc lib32-libstdc++"
RDEPENDS:${PN} += "cam-cccam-config"

FILESEXTRAPATHS:prepend := "${THISDIR}/cam-cccam:"

SRC_URI = " \
            file://cccam_versions \
            file://init221 \
"

require softcam-binary-32bit.inc

CAMNAME = "CCcam_2.2.1"

S = "${UNPACKDIR}"

FILES:${PN} = "/usr/bin/ /usr/script/"

PACKAGES = "${PN}"
PACKAGE_ARCH = "${TUNE_PKGARCH}"

do_install() {
    install -d ${D}/usr/script
    install -m 0755 ${S}/init221 ${D}/usr/script/${CAMNAME}_cam.sh
    install -d ${D}/${bindir}
    install -d ${D}/usr/bin
    install -m 0755 ${S}/cccam_versions/CCcam_${PV}_${TARGET_ARCH} ${D}/usr/bin/${CAMNAME}
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

rm -rf /usr/bin/${CAMNAME} > /dev/null 2>&1
rm -rf /usr/script/${CAMNAME}_cam.sh > /dev/null 2>&1

exit 0
}

INHIBIT_PACKAGE_STRIP = "1"
INSANE_SKIP:${PN} = "already-stripped build-deps file-rdeps"
INSANE_SKIP:${PN}_aarch64 = "already-stripped arch build-deps file-rdeps"
INSANE_SKIP:${PN} += "ldflags"
TARGET_CC_ARCH += "${LDFLAGS}"
