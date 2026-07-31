SUMMARY = "Components OctEtSkins"
MAINTAINER = "norhap"
SECTION = "base"
PRIORITY = "required"
LICENSE = "LicenseRef-proprietary"

require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "1.0+git"
PKGV = "1.0+git${GITPKGV}"
VER = "1.0"
PR = "r1"

SRC_URI = "git://github.com/openspa/OctEtSkins-components.git;protocol=https;branch=master"

FILES:${PN} = "${libdir}/enigma2/python/Components"

PACKAGES = "${PN}"

do_install() {
        install -d ${D}${libdir}/enigma2/python/Components
        cp -r --no-preserve=ownership ${S}${libdir}/enigma2/python/Components/* ${D}${libdir}/enigma2/python/Components
}

do_install:append() {
    find ${D}${PLUGINPATH} -name "*.py" -delete
}

do_package_qa[noexec] = "1"
deltask do_populate_sysroot
