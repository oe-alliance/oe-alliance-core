SUMMARY = "A components library for UPnP."
HOMEPAGE = "http://packages.python.org/cocy"
AUTHOR = "Michael N. Lipp <mnl@mnl.de>"
SECTION = "devel/python"
LICENSE = "GPL-1.0"
LIC_FILES_CHKSUM = "file://setup.py;md5=552ebae154a08bed6e46b2207714d6a6"

RDEPENDS_${PN} = "${PYTHON_PN}-circuits ${PYTHON_PN}-circuits-bricks ${PYTHON_PN}-rbtranslations ${PYTHON_PN}-tenjin"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "0.3+git${SRCPV}"
PKGV = "0.3+git${GITPKGV}"

SRC_URI = "git://github.com/mnlipp/CoCy;protocol=https"

S = "${WORKDIR}/git"

inherit setuptools3

# txt file which should go into -doc
FILES_${PN}-doc += "${datadir}/pypi-overview.rst"

include ${PYTHON_PN}-package-split.inc
