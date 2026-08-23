DESCRIPTION = "Shows the clock permanent on the screen"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc


inherit gittag

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/oe-alliance-plugins/PermanentClock.git;protocol=https;branch=main"

do_install:append() {
    install -d ${D}/usr/lib/enigma2/python/Components/Converter
    install -d ${D}/usr/lib/enigma2/python/Components/Renderer
    install -m 0755 ${S}/PermanentClock/PermanentClockTime.py ${D}/usr/lib/enigma2/python/Components/Converter/PermanentClockTime.py
    install -m 0755 ${S}/PermanentClock/PermanentClockWatches.py ${D}/usr/lib/enigma2/python/Components/Renderer/PermanentClockWatches.py
    # compileall from python3-compileall.inc has already run at this point
    python3 -m compileall -o2 -b ${D}/usr/lib/enigma2/python/Components -d /usr/lib/enigma2/python/Components
}

FILES:${PN} += "/usr/lib/enigma2/python/Components"
