LICENSE = "CLOSED"
require conf/license/license-close.inc
inherit allarch

PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r0"
SRCREV = "${AUTOREV}"

SRC_URI = "git://github.com/ciefp/ciefpsettings-enigma2.git;protocol=git"
S = "${WORKDIR}/git"

inherit gitpkgv

FILES_${PN} = "${sysconfdir}/enigma2/"

do_install () {
    install -d ${D}${sysconfdir}/enigma2
    cp -r ${S}/${SETTINGS_FILENAME}/* ${D}${sysconfdir}/enigma2
}

do_prepare_recipe_sysroot[noexec] = "1"
do_populate_sysroot[noexec] = "1"
do_package_qa[noexec] = "1"
