SUMMARY = "picons-delta-dark-on-white-220x132"
SECTION = "base"
PRIORITY = "required"
MAINTAINER = "Atom89"
inherit allarch

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
VER ="1.0"
PR = "r2"

SRC_URI="git://github.com/atom89/srp.220x132.dark-on-white-delta.git;protocol=https;branch=master"

S = "${WORKDIR}/git"

PACKAGES = "${PN}"

FILES_${PN} = "/picon/*"

do_install() {
    cp -rp ${S}/picon ${D}/
}

do_package_qa[noexec] = "1"
