DESCRIPTION = "SystemTools for enigma2 stb"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

inherit gittag

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/oe-alliance-plugins/SystemTools.git;protocol=https;branch=main"

FILES:${PN} += "${libdir}/enigma2/python/Plugins/Extensions/SystemTools"

do_install:append() {
    install -m 0755 ${S}/SystemTools/memorysimple.sh ${D}${libdir}/enigma2/python/Plugins/Extensions/SystemTools/memorysimple.sh
}