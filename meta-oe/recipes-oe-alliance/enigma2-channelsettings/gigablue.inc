LICENSE = "CLOSED"
require conf/license/license-close.inc

PV = "1.0+git${SRCPV}"
PKGV = "${GITPKGVTAG}"
PR = "r1"
SRCREV = "${AUTOREV}"

SRC_URI = "git://github.com/openmips/settings.git;protocol=git"
S = "${WORKDIR}/git"

inherit gitpkgv allarch

FILES_${PN} = "/etc/enigma2/"

do_install () {
    install -d ${D}/etc/enigma2
    cp -r ${S}/${SETTINGS_FILENAME}/* ${D}/etc/enigma2
}
