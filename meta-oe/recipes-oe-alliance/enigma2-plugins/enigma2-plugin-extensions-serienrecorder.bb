DESCRIPTION = "Erstellt automatisiert Timer fuer deine Lieblings-Serien - nie mehr eine Folge verpassen!"
SUMMARY = "Erstellt automatisiert Timer fuer deine Lieblings-Serien - nie mehr eine Folge verpassen!"
HOMEPAGE = "http://www.serienserver.de"
SECTION = "extra"
PRIORITY = "optional"
MAINTAINER = "einfall & w22754 & egn & MacDisein"

require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

RDEPENDS:${PN} = "${PYTHON_PN}-sqlite3 ${PYTHON_PN}-json ${PYTHON_PN}-xmlrpc ${PYTHON_PN}-email"

inherit gittag allarch

SRCREV = "${AUTOREV}"
PV = "git${SRCPV}"
PKGV = "${GITPKGVTAG}"

SRC_URI = "git://github.com/einfall/serienrecorder.git;protocol=https;branch=master"

S = "${WORKDIR}/git/src"

PLUGINPATH = "${libdir}/enigma2/python/Plugins/Extensions/serienrecorder"

FILES:${PN} = "${PLUGINPATH}"

do_install() {
    install -d ${D}${PLUGINPATH}
    cp -rf ${S}/* ${D}${PLUGINPATH}
}

pkg_postrm:${PN}() {
#!/bin/sh

echo "* POSTRM: deleting files"
rm -rf /usr/lib/enigma2/python/Plugins/Extensions/serienrecorder

exit 0
}
