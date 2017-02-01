SUMMARY = "SpecialJump - Fast manual skipping of commercials, and more..."
MAINTAINER = "www.opena.tv Fischreiher"
SECTION = "base"
LICENSE = "proprietary"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "0.0+git${SRCPV}"
PKGV = "0.0+git${GITPKGV}"
VER ="0.0"
PR = "r0"

SRC_URI="git://github.com/openatv/SpecialJump.git"

S = "${WORKDIR}/git"

FILES_${PN} = "/usr/*"

CONFFILES_${PN} = "/usr/lib/enigma2/python/Plugins/Extensions/SpecialJump/keymap_user.xml"

do_install() {
    cp -rp ${S}/usr ${D}/
}

pkg_postrm_${PN}() {
#!/bin/sh
#
# cleanup script, executed after removing plugin
#

rm -rf /usr/lib/enigma2/python/Plugins/Extensions/SpecialJump/ > /dev/null 2>&1
echo "Plugin SpecialJump removed! You should restart enigma2 now!"
exit 0
}

do_package_qa[noexec] = "1"
