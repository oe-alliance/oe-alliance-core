SUMMARY = "opendroid-base-files"
MAINTAINER = "opendroid"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "3.0+git${SRCPV}"
PKGV = "3.0+git${GITPKGV}"
VER ="3.0"
PR = "r13"

SRC_URI="git://github.com/opendroid-Team/opendroid-base.git"

S = "${WORKDIR}/git"

FILES_${PN} = "/*"

do_install() {
    cp -rp ${S}/* ${D}/
    mkdir -p ${D}/usr/scripts
}
