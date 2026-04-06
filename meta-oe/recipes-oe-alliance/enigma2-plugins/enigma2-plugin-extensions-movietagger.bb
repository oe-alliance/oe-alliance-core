DESCRIPTION = "MovieTagger"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc


inherit gittag

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/oe-alliance-plugins/MovieTagger.git;protocol=https;branch=main"

CONFFILES = "/etc/enigma2/movietags"

do_install:append() {
    install -d ${D}/etc/enigma2
    install -m 0755 ${S}/MovieTagger/movietags ${D}/etc/enigma2/movietags
}

FILES:${PN} += "/etc/enigma2/movietags"
