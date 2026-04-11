DESCRIPTION = "JustWatch streaming search for Enigma2"
require conf/license/license-gplv2.inc

RDEPENDS:${PN} = "python3-twisted-web python3-simple-justwatch-python-api "

inherit gittag

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/oe-alliance-plugins/JustWatch.git;protocol=https;branch=main"

do_install:append() {
    install -d ${D}/usr/lib/enigma2/python/Components/Renderer
    install -m 0644 ${S}/JustWatch/Renderer/JustWatchVRunningText.py ${D}/usr/lib/enigma2/python/Components/Renderer/JustWatchVRunningText.py
}

FILES:${PN} += "/usr/lib/enigma2/python/Components/Renderer"

require conf/python/python3-compileall.inc
