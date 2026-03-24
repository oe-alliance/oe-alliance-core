SUMMARY  = "Protego is a pure-Python robots.txt parser with support for modern conventions."
HOMEPAGE = "https://pypi.org/project/Protego/#description"
SECTION = "devel/python"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4d4c85a6e830a1da71d07eebd4017802"

DEPENDS += "python3-hatch-vcs-native"

SRC_URI[md5sum] = "c70f1395f80f39a18f6588f5eae721e4"
SRC_URI[sha256sum] = "3466f41438421cf90008e98534d5fde47dc16a17482571d021143ac18b70ace9"

inherit pypi python_hatchling

include python3-package-split.inc
