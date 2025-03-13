SUMMARY  = "Protego is a pure-Python robots.txt parser with support for modern conventions."
HOMEPAGE = "https://pypi.org/project/Protego/#description"
SECTION = "devel/python"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4d4c85a6e830a1da71d07eebd4017802"

SRC_URI[md5sum] = "88cf91f9691acb9bfb12fcedb4b8b8c9"
SRC_URI[sha256sum] = "93a5e662b61399a0e1f208a324f2c6ea95b23ee39e6cbf2c96246da4a656c2f6"

inherit pypi setuptools3_legacy

include python3-package-split.inc
