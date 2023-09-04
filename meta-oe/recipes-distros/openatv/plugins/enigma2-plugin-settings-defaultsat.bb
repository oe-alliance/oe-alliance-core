SUMMARY = "openATV default SAT Settings Archiv"
MAINTAINER = "openATV Team"
SECTION = "base"
LICENSE = "proprietary"
inherit allarch

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "${IMAGE_VERSION}+git"
PKGV = "${IMAGE_VERSION}+git${GITPKGV}"
VER ="${IMAGE_VERSION}"
PR = "r0"

SRC_URI="git://github.com/openatv/enigma2-plugin-settings-defaultsat.git;protocol=https;branch=master"

S = "${WORKDIR}/git"

FILES:${PN} = "/etc/defaultsat.tar.gz"


do_install() {
    install -d ${D}/${sysconfdir}
    /usr/bin/fakeroot tar -czf ${D}/${sysconfdir}/defaultsat.tar.gz -C ${S}/etc/enigma2 .
}