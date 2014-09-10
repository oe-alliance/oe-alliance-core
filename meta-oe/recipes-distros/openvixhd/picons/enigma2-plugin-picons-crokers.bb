SUMMARY = "picons-crokers"
SECTION = "base"
PRIORITY = "required"
MAINTAINER = "rossi2000"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "2.3+git${SRCPV}"
PKGV = "2.3+git${GITPKGV}"
PR = "r5"


SRC_URI="git://github.com/rossi2000/enigma2-plugin-picons-crokers.git;protocol=git"

S = "${WORKDIR}/git"

FILES_${PN} = "/picon"

do_install() {
    install -d ${D}/picon
    install -m 0644 ${WORKDIR}/git/picon/*.* ${D}/picon
}

do_install_append() {
    find ${D}/picon/ -name 'picon_default.png' -exec rm {} \;
}
