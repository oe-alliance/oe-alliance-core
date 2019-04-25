SUMMARY = "cssselect parses CSS3 Selectors and translates them to XPath 1.0"
HOMEPAGE = "https://pypi.org/project/cssselect"
SECTION = "devel/python"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=952026b3fd2f625f2a3c0aa21da2493d"

SRC_URI = "https://files.pythonhosted.org/packages/52/ea/f31e1d2e9eb130fda2a631e22eac369dc644e8807345fbed5113f2d6f92b/cssselect-${PV}.tar.gz"

SRC_URI[md5sum] = "50422c9ec04b74cd60c571f74ddc1a80"
SRC_URI[sha256sum] = "066d8bc5229af09617e24b3ca4d52f1f9092d9e061931f4184cd572885c23204"

S = "${WORKDIR}/cssselect-${PV}"

inherit setuptools

include python-package-split.inc
