DESCRIPTION = "SeriesPlugin"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

RDEPENDS:${PN} = "python3-difflib python3-json python3-xml python3-xmlrpc"

inherit gittag

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/oe-alliance-plugins/SeriesPlugin.git;protocol=https;branch=main"

CONFFILES = "/etc/enigma2/seriesplugin_pattern_directories.json /etc/enigma2/seriesplugin_patterns.json"

do_install:append() {
    install -d ${D}/etc/enigma2
    install -m 0755 ${S}/SeriesPlugin/seriesplugin_pattern_directories.json ${D}/etc/enigma2/seriesplugin_pattern_directories.json
    install -m 0755 ${S}/SeriesPlugin/seriesplugin_patterns.json ${D}/etc/enigma2/seriesplugin_patterns.json
}

FILES:${PN} += "/etc/enigma2/"