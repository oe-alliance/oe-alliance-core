SUMMARY = "GigaBlue Enigma2 Skin PAX FHD"
MAINTAINER = "openmips"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "${IMAGE_VERSION}+git${SRCPV}"
PKGV = "${IMAGE_VERSION}+git${GITPKGV}"
VER ="${IMAGE_VERSION}"
PR = "r0"

DEPENDS = "enigma2-plugin-skins-pax"

SRC_URI="git://github.com/openmips/skin-pax-fhd.git;protocol=git"

S = "${WORKDIR}/git"

FILES_${PN} = "/usr/*"

do_install() {
    cp -rp ${S}/usr ${D}/
}

do_package_qa[noexec] = "1"