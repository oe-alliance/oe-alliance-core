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
PKGV = "${IMAGE_VERSION}+git${GITPKGV}"
VER ="${IMAGE_VERSION}"
PR = "r0"

SRC_URI ?= "${@bb.utils.contains("DISTRO_TYPE", "release", "git://github.com/openmips/skin-pax.git;protocol=git" , "git://git@gitlab.openmips.com/dev-openmips/openmips-skin.git;protocol=ssh;branch=master", d)}"

S = "${WORKDIR}/git"

FILES_${PN} = "/usr/*"

do_install() {
    cp -rp ${S}/usr ${D}/
}

do_package_qa[noexec] = "1"