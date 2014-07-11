MAINTAINER = "oe-alliance team"
LICENSE = "CLOSED"

inherit gitpkgv allarch

SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r6"

SRC_URI = "git://github.com/oe-alliance/oe-alliance-settings.git;protocol=git"
S = "${WORKDIR}/git"

FILES_${PN} = "/etc/enigma2/"

do_install () {
    install -d ${D}/etc/enigma2
    cp -r ${S}/${SETTINGS_FILENAME}/* ${D}/etc/enigma2
}
