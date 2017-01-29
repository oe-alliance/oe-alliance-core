LICENSE = "CLOSED"
require conf/license/license-close.inc
PACKAGE_ARCH = "all"

PV = "1.0+git${SRCPV}"
PKGV = "${GITPKGVTAG}"
PR = "r1"
SRCREV = "${AUTOREV}"

SRC_URI = "git://github.com/openmips/settings.git;protocol=git"
S = "${WORKDIR}/git"

inherit gitpkgv

FILES_${PN} = "/etc/enigma2/"

do_install () {
    install -d ${D}/etc/enigma2
    cp -r ${S}/${SETTINGS_FILENAME}/* ${D}/etc/enigma2
}

do_prepare_recipe_sysroot[noexec] = "1"
do_populate_sysroot[noexec] = "1"
do_package_qa[noexec] = "1"
