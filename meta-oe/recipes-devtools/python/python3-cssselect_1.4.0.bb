SUMMARY = "cssselect parses CSS3 Selectors and translates them to XPath 1.0"
HOMEPAGE = "https://github.com/scrapy/cssselect"
SECTION = "devel/python"
AUTHOR = "Ian Bicking <ianb@colorstudy.com>"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=952026b3fd2f625f2a3c0aa21da2493d"

DEPENDS += "python3-hatch-vcs-native"

SRC_URI[md5sum] = "3aca561a6fe4e51d2993e0a0f604e30a"
SRC_URI[sha256sum] = "fdaf0a1425e17dfe8c5cf66191d211b357cf7872ae8afc4c6762ddd8ac47fc92"

inherit pypi python_hatchling

include python3-package-split.inc
