SUMMARY = "OscamSmartcard"
MAINTAINER = "undertaker"
SECTION = "base"
LICENSE = "GPL-2.0-only"
inherit allarch

require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

inherit gitpkgv ${PYTHON_PN}native

SRCREV = "${AUTOREV}"
PV = "2.4+git"
PKGV = "2.4+git${GITPKGV}"
VER ="2.4"
PR = "r0"

SRC_URI="git://github.com/gigablue-support-org/oscamsmartcard.git;protocol=https;branch=master"

S = "${WORKDIR}/git"

FILES:${PN} = "/usr/*"

do_install() {
    cp -rp ${S}/usr ${D}/
}

pkg_postrm:${PN}() {
#!/bin/sh
rm -rf /usr/lib/enigma2/python/Plugins/Extensions/OscamSmartcard 2>/dev/null
echo "**************************************************"
echo "* Successfully removed OscamSmartcard!           *"
echo "**************************************************"
exit 0
}

do_package_qa[noexec] = "1"
