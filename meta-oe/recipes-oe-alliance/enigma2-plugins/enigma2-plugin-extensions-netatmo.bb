SUMMARY = "Netatmo weather station plugin for Enigma2"
MAINTAINER = "Benni16v"
SECTION = "base"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "1.7.4+git"
PKGV = "1.7.4+git${GITPKGV}"
PR = "r0"

SRC_URI = "git://github.com/Benni16v/Netatmo-enigma2.git;branch=main;protocol=https"

RDEPENDS:${PN} = "python3-io python3-json python3-netclient python3-twisted-core"

FILES:${PN} = "${libdir}"

do_install() {
    install -d ${D}${libdir}/enigma2/python
    cp -r --no-preserve=ownership ${S}/Components ${S}/Plugins ${D}${libdir}/enigma2/python/
}
