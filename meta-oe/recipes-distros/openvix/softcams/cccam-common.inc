# Stuff applying to all CCcams on image
SUMMARY:${PN} = "CCcam ${PKGV}"
DESCRIPTION:${PN} = "An outdated binary only softcam"

PR = "r1-de"

LICENSE = "CLOSED"
require conf/license/license-close.inc

FILESEXTRAPATHS:prepend := "${THISDIR}/enigma2-plugin-softcams-cccam:"

SRC_URI = " \
            file://cccam_versions \
            file://CCcam.cfg \
"

require softcam.inc
require softcam-binary-32bit.inc

CONFFILES:${PN} ?= "/etc/CCcam.cfg"

PACKAGE_ARCH = "${TUNE_PKGARCH}"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

do_install() {
    install -d ${D}/${bindir}
    install -d ${D}/usr/softcams
    install -m 0755 ${S}/cccam_versions/CCcam_${PV}_${TARGET_ARCH} ${D}/usr/softcams/${CAMNAME}
    install -d ${D}/etc
    install -m 0755 ${S}/CCcam.cfg ${D}/etc/CCcam.cfg
}

RDEPENDS:${PN} = "glibc libstdc++ softcam-support"
RDEPENDS:${PN}_aarch64 = "lib32-glibc lib32-libstdc++ softcam-support"

# libxcrypt

INHIBIT_PACKAGE_STRIP = "1"
INSANE_SKIP:${PN} = "already-stripped build-deps file-rdeps"
INSANE_SKIP:${PN}_aarch64 = "already-stripped arch build-deps file-rdeps"

RPROVIDES:${PN} += "enigma2-plugin-softcams-cccam-2.3.2 enigma2-plugin-softcams-cccam-2.3.9"
RREPLACES:${PN} += "enigma2-plugin-softcams-cccam-2.3.2 enigma2-plugin-softcams-cccam-2.3.9"
RCONFLICTS:${PN} += "enigma2-plugin-softcams-cccam-2.3.2 enigma2-plugin-softcams-cccam-2.3.9"
