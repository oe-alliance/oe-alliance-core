SUMMARY  = "This is a Python library of web-related functions"
HOMEPAGE = "https://github.com/scrapy/w3lib"
SECTION = "devel/python"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=94fe8a350dbc22aca301c15a4d213b48"

DEPENDS += "python3-hatch-vcs-native"

SRC_URI[md5sum] = "11b4a00c75a723fd3e5623b8b8214de0"
SRC_URI[sha256sum] = "8dd69ee39ff6398d708c793abc779c334a69bac7cee1cdf71736c669ed6be864"

inherit pypi python_hatchling

include python3-package-split.inc
