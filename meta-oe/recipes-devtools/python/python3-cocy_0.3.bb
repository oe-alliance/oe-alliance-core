SUMMARY = "A components library for UPnP."
HOMEPAGE = "http://packages.python.org/cocy"
AUTHOR = "Michael N. Lipp <mnl@mnl.de>"
SECTION = "devel/python"
LICENSE = "GPL-1.0-only"
LIC_FILES_CHKSUM = "file://setup.py;md5=552ebae154a08bed6e46b2207714d6a6"

RDEPENDS:${PN} = "python3-circuits python3-circuits-bricks python3-rbtranslations python3-tenjin"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "0.3+git"
PKGV = "0.3+git${GITPKGV}"

SRC_URI = "git://github.com/mnlipp/CoCy;protocol=https;branch=master"

S = "${WORKDIR}/git"

inherit setuptools3

# txt file which should go into -doc
FILES:${PN}-doc += "${datadir}/pypi-overview.rst"

include python3-package-split.inc
