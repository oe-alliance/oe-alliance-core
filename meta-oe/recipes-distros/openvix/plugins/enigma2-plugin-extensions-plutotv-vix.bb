DESCRIPTION = "PlutoTV plugin for enigma2"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://src/LICENSE;md5=c644709e9dad24bd9bf90ac96687ed2f"

RDEPENDS:${PN} = "python3-requests"

require conf/python/python3-compileall.inc
inherit gitpkgv allarch gettext setuptools3-openplugins

PV = "1.0+git"
PKGV = "1.0+git${GITPKGV}"

SRCREV = "${AUTOREV}"

SRC_URI = "git://github.com/OpenViX/PlutoTV.git;protocol=https;branch=master"
