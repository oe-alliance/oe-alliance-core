DESCRIPTION = "NewsReader for reading RSS-feeds"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

inherit gittag

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/oe-alliance-plugins/NewsReader.git;protocol=https;branch=main"

do_install:append() {
    install -d ${D}/etc/
    install -m 0755 ${S}/NewsReader/data/feeds.xml ${D}/etc/feeds.xml
}


FILES:${PN} += "/etc/feeds.xml"
CONFFILES:${PN} = "/etc/feeds.xml"
