SUMMARY = "GigaBlue Enigma2 Skin PAX"
MAINTAINER = "teamblue"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
inherit allarch

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "${IMAGE_VERSION}+git${SRCPV}"
PKGV = "${IMAGE_VERSION}+git${GITPKGV}"
VER ="${IMAGE_VERSION}"
PR = "r4"

SRC_URI="${@bb.utils.contains("DISTRO_TYPE", "release", "git://github.com/teamblue-e2/skin.git;protocol=git;branch=master" , "git://github.com/teamblue-e2/skin.git;protocol=git;branch=DEV", d)}"

S = "${WORKDIR}/git"

FILES_${PN} = "/usr/*"

do_install() {
    cp -rp ${S}/usr ${D}/
}

do_package_qa[noexec] = "1"
