SUMMARY = "GigaBlue Enigma2 Skin PAX V2"
MAINTAINER = "teamblue"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
inherit allarch

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "${IMAGE_VERSION}+git"
PKGV = "${IMAGE_VERSION}+git${GITPKGV}"
VER ="${IMAGE_VERSION}"
PR = "r1"


RDEPENDS:${PN} = "${PYTHON_PN}-requests \
                  ${PYTHON_PN}-lxml\
"

SRC_URI="git://github.com/teamblue-e2/skin.git;protocol=https;branch=master"

S = "${WORKDIR}/git"

FILES:${PN} = "/usr/*"

do_install() {
    cp -rp ${S}/usr ${D}/
    mv ${D}/usr/share/enigma2/GigabluePax ${D}/usr/share/enigma2/GigabluePaxV2
}

do_package_qa[noexec] = "1"
