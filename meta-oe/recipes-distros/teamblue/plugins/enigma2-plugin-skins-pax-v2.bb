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
VER = "${IMAGE_VERSION}"
PR = "r1"

RDEPENDS:${PN} = "python3-requests \
                  python3-lxml\
"

SRC_URI = "git://github.com/teamblue-e2/skin.git;protocol=https;branch=master"

FILES:${PN} = "/usr/*"

do_install() {
    cp -r --no-preserve=ownership ${S}/usr ${D}/
    mv ${D}/usr/share/enigma2/GigabluePax ${D}/usr/share/enigma2/GigabluePaxV2
}

do_package_qa[noexec] = "1"
