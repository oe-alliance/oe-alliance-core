SUMMARY = "Pure python implementation of magic file detection"
HOMEPAGE = "https://github.com/cdgriffith/puremagic"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ac85ec1f210835a5806bc00df0be30d7"

DEPENDS += "python3-setuptools-scm-native"

SRC_URI[md5sum] = "0e239f1d432c951d8811447b739a8e34"
SRC_URI[sha256sum] = "eb4bddf07c177c4b434554b92165b67449f5a51e152b976202d6254498810eef"

inherit pypi python_setuptools_build_meta

include python3-package-split.inc
