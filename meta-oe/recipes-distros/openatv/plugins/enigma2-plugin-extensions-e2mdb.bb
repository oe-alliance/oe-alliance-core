DESCRIPTION = "e2 media database and scanner"
MAINTAINER = "OpenATV Team"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

RDEPENDS:${PN} = "python3-sqlite3"

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

inherit setuptools3-openplugins pkgconfig update-rc.d

SRC_URI = "git://github.com/openatv/e2MDB.git;protocol=https;branch=main"

INITSCRIPT_NAME = "e2mdbd"
INITSCRIPT_PARAMS = "defaults 90 10"
INITSCRIPT_PACKAGES = "${PN}"

FILES:${PN} += " \
    ${libdir}/enigma2/python/Plugins/Extensions/e2MDB \
    ${libdir}/enigma2/python/Components/Converter \
    ${sysconfdir}/init.d/e2mdbd \
"

CONFFILES:${PN} += "${sysconfdir}/init.d/e2mdbd"

do_install:append() {
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${S}/init.d/e2mdbd ${D}${sysconfdir}/init.d/e2mdbd
}
