DESCRIPTION = "DVB-I channel lists, broadcast fallback, picons and EPG for Enigma2"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

inherit gittag

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/oe-alliance-plugins/DvbIManager.git;protocol=https;branch=main"

RDEPENDS:${PN} = "python3-asyncio python3-compression python3-crypt python3-json python3-netclient python3-pillow python3-xml"

do_install:append() {
    install -d ${D}${sysconfdir}/tuxbox
    install -m 0644 ${S}/dvbi.xml ${D}${sysconfdir}/tuxbox/dvbi.xml
}

FILES:${PN} += "${sysconfdir}/tuxbox/dvbi.xml"
CONFFILES:${PN} += "${sysconfdir}/tuxbox/dvbi.xml"
