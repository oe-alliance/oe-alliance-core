SUMMARY = "GigaBlue Enigma2 Skin PAX"
MAINTAINER = "openmips"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "${IMAGE_VERSION}+git${SRCPV}"
PKGV = "${GITPKGVTAG}"
VER ="${IMAGE_VERSION}"
PR = "r1"

SRC_URI="git://github.com/openmips/skin-pax.git;protocol=git"

S = "${WORKDIR}/git"

FILES_${PN} = "/usr/*"

do_install() {
    cp -rp ${S}/usr ${D}/
}

do_package_qa[noexec] = "1"