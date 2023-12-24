SUMMARY = "SpecialJump - Fast manual skipping of commercials, and more..."
MAINTAINER = "www.opena.tv Fischreiher"
SECTION = "base"
LICENSE = "proprietary"
DEPENDS = "${PYTHON_PN}-six-native"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "0.0+git"
PKGV = "0.0+git${GITPKGV}"
VER ="0.0"
PR = "r0"

SRC_URI="git://github.com/openatv/SpecialJump.git;branch=python3;protocol=https"

S = "${WORKDIR}/git"

FILES:${PN} = "${libdir}"

CONFFILES:${PN} = "/usr/lib/enigma2/python/Plugins/Extensions/SpecialJump/keymap_user.xml"

do_install() {
    install -d ${D}${libdir}
    cp -rp ${S}/usr/lib/* ${D}${libdir}/
}

pkg_postrm:${PN}() {
#!/bin/sh
#
# cleanup script, executed after removing plugin
#

rm -rf ${libdir}/enigma2/python/Plugins/Extensions/SpecialJump/ > /dev/null 2>&1
echo "Plugin SpecialJump removed! You should restart enigma2 now!"
exit 0
}

do_package_qa[noexec] = "1"
