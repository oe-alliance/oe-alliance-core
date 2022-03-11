SUMMARY  = "Protego is a pure-Python robots.txt parser with support for modern conventions."
HOMEPAGE = "https://pypi.org/project/Protego/#description"
SECTION = "devel/python"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4d4c85a6e830a1da71d07eebd4017802"

SRC_URI = "https://files.pythonhosted.org/packages/db/6e/bf6d5e4d7cf233b785719aaec2c38f027b9c2ed980a0015ec1a1cced4893/Protego-0.1.16.tar.gz"

SRC_URI[md5sum] = "5a99cba555740e32acdcb2dae3948f8e"
SRC_URI[sha256sum] = "a682771bc7b51b2ff41466460896c1a5a653f9a1e71639ef365a72e66d8734b4"

S = "${WORKDIR}/Protego-${PV}"

inherit setuptools3_legacy

include ${PYTHON_PN}-package-split.inc
