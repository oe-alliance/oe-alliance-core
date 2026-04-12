DESCRIPTION = "PPanel"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

inherit gittag

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/oe-alliance-plugins/PPanel.git;protocol=https;branch=main"

do_install:append() {
    install -d ${D}/etc/ppanel
    install -m 0755 ${S}/PPanel/data/PPanel_tutorial.xml ${D}/etc/ppanel/PPanel_tutorial.xml
}


PACKAGES =+ "${PN}-example"
FILES:${PN}-example = "/etc/ppanel/PPanel_tutorial.xml"
