SUMMARY = "Components for UPnP."
HOMEPAGE = "http://github.com/mnlipp/CoCy"
SECTION = "devel/python"
LICENSE = "AGPL-3.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=9908f765b999cf8858b921c1cdacc5a7"

DEPENDS = "python-circuits python-circuits-bricks"
RDEPENDS_${PN} = "python-circuits python-circuits-bricks"

SRC_URI = "git://github.com/mnlipp/CoCy;protocol=https"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "0.3+git${SRCPV}"
PKGV = "0.3+git${GITPKGV}"

S = "${WORKDIR}/git"

inherit setuptools

# some txt files which should go into -doc
FILES_${PN}-doc += " \
    /usr/share/pypi-overview.rst \
"

include python-package-split.inc
