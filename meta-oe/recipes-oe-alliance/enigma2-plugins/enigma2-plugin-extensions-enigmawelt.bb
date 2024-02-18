SUMMARY = "The biggest DreamOS/Enigma2 Video Blog"
MAINTAINER = "enigmawelt jbleyel CommanderData2338"
HOMEPAGE = "https://enigmawelt.de"
SECTION = "base"
inherit allarch

require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "1.3.1+git${SRCPV}"
PKGV = "1.3.1+git${GITPKGV}"
VER ="1.3.1"
PR = "r0"

SRC_URI="git://github.com/enigmawelt/enigmawelt.de.git;protocol=https;branch=main"

S = "${WORKDIR}/git"

FILES:${PN} = "${libdir}"

do_install() {
    install -d ${D}${libdir}/enigma2/python/Plugins/Extensions/Enigmawelt
    cp -rf ${S}/src/* ${D}${libdir}/enigma2/python/Plugins/Extensions/Enigmawelt/
}

pkg_postrm:${PN}() {
#!/bin/sh
rm -r ${libdir}/enigma2/python/Plugins/Extensions/Enigmawelt 2>/dev/null
exit 0
}

do_package_qa[noexec] = "1"
