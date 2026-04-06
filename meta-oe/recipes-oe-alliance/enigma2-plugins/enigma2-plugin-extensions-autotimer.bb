DESCRIPTION = "Automatically add Timers based on simple rules"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

RDEPENDS:${PN} = "python3-codecs python3-difflib python3-threading python3-xml enigma2-plugin-systemplugins-mphelp"

inherit gittag

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/oe-alliance-plugins/AutoTimer.git;protocol=https;branch=main"

CONFFILES:${PN} = "${sysconfdir}/enigma2/autotimer.xml"

do_install:append() {
    install -d ${D}${sysconfdir}/enigma2
    install -m 0755 ${S}/etc/autotimer.xml ${D}${sysconfdir}/enigma2/autotimer.xml
}

FILES:${PN} += "${sysconfdir}/enigma2/autotimer.xml"
