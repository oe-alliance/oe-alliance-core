SUMMARY  = "Protego is a pure-Python robots.txt parser with support for modern conventions."
HOMEPAGE = "https://pypi.org/project/Protego/#description"
SECTION = "devel/python"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4d4c85a6e830a1da71d07eebd4017802"

PYPI_PACKAGE = "Protego"

SRC_URI[md5sum] = "200c5f8947240a59ecee2b12efd26fd5"
SRC_URI[sha256sum] = "e94430d0d25cbbf239bc849d86c5e544fbde531fcccfa059953c7da344a1712c"

inherit pypi setuptools3_legacy

include ${PYTHON_PN}-package-split.inc
