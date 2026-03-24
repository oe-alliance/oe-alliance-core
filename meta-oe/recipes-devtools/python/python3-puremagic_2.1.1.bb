SUMMARY = "Pure python implementation of magic file detection"
HOMEPAGE = "https://github.com/cdgriffith/puremagic"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ac85ec1f210835a5806bc00df0be30d7"

DEPENDS += "python3-setuptools-scm-native"

SRC_URI[md5sum] = "6754aef1bc2f053ddca4ae581652c04e"
SRC_URI[sha256sum] = "b156c4ae63d84842f92a85cd49c9b9029a4f107f98ad14e7584ed652954feff4"

inherit pypi python_setuptools_build_meta

include python3-package-split.inc
