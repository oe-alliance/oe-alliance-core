SUMMARY = "A Python wrapper for TheTVDb Database API v2"
HOMEPAGE = "https://travis-ci.org/phate89/tvdbsimple"
AUTHOR = "phate89"
SECTION = "devel/python"
LICENSE = "GPL-1.0-only"
LIC_FILES_CHKSUM = "file://setup.py;md5=0cb2931fa73dc0823eb8c23edda98809"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "1.0.6+git${SRCPV}"
PKGV = "1.0.6.3+git${GITPKGV}"

SRC_URI = "git://github.com/phate89/tvdbsimple.git;protocol=https;branch=master"

S = "${WORKDIR}/git"

inherit setuptools3

include ${PYTHON_PN}-package-split.inc
