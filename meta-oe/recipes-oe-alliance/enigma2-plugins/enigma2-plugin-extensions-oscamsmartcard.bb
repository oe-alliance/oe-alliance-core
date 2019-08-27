SUMMARY = "OscamSmartcard"
MAINTAINER = "undertaker"
SECTION = "base"
LICENSE = "GPLv2"
inherit allarch

require conf/license/license-gplv2.inc

inherit gitpkgv pythonnative

SRCREV = "${AUTOREV}"
PV = "2.4+git${SRCPV}"
PKGV = "2.4+git${GITPKGV}"
VER ="2.4"
PR = "r0"

SRC_URI="git://github.com/gigablue-support-org/oscamsmartcard.git"

S = "${WORKDIR}/git"

FILES_${PN} = "/usr/*"

do_install() {
    cp -rp ${S}/usr ${D}/
}

pkg_postrm_${PN}() {
#!/bin/sh
rm -rf /usr/lib/enigma2/python/Plugins/Extensions/OscamSmartcard 2>/dev/null
echo "**************************************************"
echo "* Successfully removed OscamSmartcard!           *"
echo "**************************************************"
exit 0
}

do_package_qa[noexec] = "1"
