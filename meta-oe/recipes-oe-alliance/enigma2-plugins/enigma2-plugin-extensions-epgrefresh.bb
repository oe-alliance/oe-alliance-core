DESCRIPTION = "Plugin to refresh EPG Data when Receiver is inactive"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

RDEPENDS:${PN} = "python3-codecs python3-xml enigma2-plugin-systemplugins-mphelp"

inherit gittag

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/oe-alliance-plugins/EPGRefresh.git;protocol=https;branch=main"

CONFFILES:${PN} = "${sysconfdir}/enigma2/epgrefresh.xml"

do_install:append() {
    install -d ${D}${sysconfdir}/enigma2
    install -m 0755 ${S}/etc/epgrefresh.xml ${D}${sysconfdir}/enigma2/epgrefresh.xml
}

FILES:${PN} += "${sysconfdir}/enigma2/epgrefresh.xml"
