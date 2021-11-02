SUMMARY = "openATV default SAT Settings Archiv"
MAINTAINER = "openATV Team"
SECTION = "base"
LICENSE = "proprietary"
inherit allarch

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "6.0+git${SRCPV}"
PKGV = "6.0+git${GITPKGV}"
VER ="6.0"
PR = "r0"

SRC_URI="git://github.com/openatv/enigma2-plugin-settings-defaultsat.git;protocol=https"

S = "${WORKDIR}/git"

FILES_${PN} = "/etc/defaultsat.tar.gz"


do_install() {
    install -d ${D}/${sysconfdir}
    tar -czf ${D}/${sysconfdir}/defaultsat.tar.gz -C ${S}/etc/enigma2 .
}