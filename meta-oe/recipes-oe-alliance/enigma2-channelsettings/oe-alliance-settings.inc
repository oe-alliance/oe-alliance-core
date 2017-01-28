MAINTAINER = "oe-alliance team"
LICENSE = "CLOSED"
require conf/license/license-close.inc
PACKAGE_ARCH = "all"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "${GITPKGVTAG}"
PR = "r6"

SRC_URI = "git://github.com/oe-alliance/oe-alliance-settings.git;protocol=git"
S = "${WORKDIR}/git"

FILES_${PN} = "/etc/enigma2/"

do_install () {
    install -d ${D}/etc/enigma2
    cp -r ${S}/${SETTINGS_FILENAME}/* ${D}/etc/enigma2
}
