SUMMARY:${PN} = "cccam-config"
DESCRIPTION:${PN} = "Example CCcam.cfg file"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PR = "r1"

FILESEXTRAPATHS:prepend := "${THISDIR}/cam-cccam:"

SRC_URI = "file://CCcam.cfg.info"

PACKAGES = "${PN}"

FILES:${PN} += "/etc/CCcam.cfg.info"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

do_install() {
    install -d ${D}/etc
    install -m 0755 ${S}/CCcam.cfg.info ${D}/etc/CCcam.cfg.info
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

rm -rf /etc/CCcam.cfg.info > /dev/null 2>&1

exit 0
}

INHIBIT_PACKAGE_STRIP = "1"
INSANE_SKIP:${PN} = "already-stripped build-deps file-rdeps"
INSANE_SKIP:${PN}_aarch64 = "already-stripped arch build-deps file-rdeps"

INSANE_SKIP:${PN} += "ldflags"

TARGET_CC_ARCH += "${LDFLAGS}"
