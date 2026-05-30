SUMMARY = "Enigma2 Skin OctEtFHD"
MAINTAINER = "norhap"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"

require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

RDEPENDS:${PN} += "enigma2-plugin-extensions-weatherplugin-spa enigma2-plugin-skins-octetcomponent"

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "1.0+git"
PKGV = "1.0+git${GITPKGV}"
VER = "1.0"
PR = "r1"

SRC_URI = "git://github.com/openspa/OctEtFHD-skin.git;protocol=https;branch=master"

FILES:${PN} = "/usr/share/enigma2"

PACKAGES = "${PN}"

do_package_qa[noexec] = "1"
deltask do_populate_sysroot

do_install() {
    cp -r --no-preserve=ownership ${S}${prefix} ${D}/
}
