SUMMARY = "GigaBlue Enigma2 Skin PAX"
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
PR = "r4"

SRC_URI = "${@bb.utils.contains("DISTRO_TYPE", "release", "git://github.com/teamblue-e2/skin.git;protocol=https;branch=master", "git://github.com/teamblue-e2/skin.git;protocol=https;branch=DEV", d)}"

FILES:${PN} = "/usr/*"

do_install() {
    cp -r --no-preserve=ownership ${S}/usr ${D}/
}

do_package_qa[noexec] = "1"
