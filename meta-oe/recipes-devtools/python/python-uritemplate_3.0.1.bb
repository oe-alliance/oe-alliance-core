SUMMARY = "Simple python library to deal with URI Templates."
HOMEPAGE = "https://uritemplate.readthedocs.org"
SECTION = "devel/python"
LICENSE = "Apache-2.0 | BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f2e14cc8f5f696fd9d47092e992265c1"

RDEPENDS_${PN} = "python"

SRC_URI = "https://files.pythonhosted.org/packages/42/da/fa9aca2d866f932f17703b3b5edb7b17114bb261122b6e535ef0d9f618f8/uritemplate-3.0.1.tar.gz"
SRC_URI[md5sum] = "869fb44fbd56713490db7272eb36c8ae"
SRC_URI[sha256sum] = "5af8ad10cec94f215e3f48112de2022e1d5a37ed427fbd88652fa908f2ab7cae"

S = "${WORKDIR}/uritemplate-${PV}"

inherit distutils

include ${PYTHON_PN}-package-split.inc
