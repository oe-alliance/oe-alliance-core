SUMMARY = "Composable complex class support for attrs and dataclasses."
HOMEPAGE = "https://github.com/Tinche/cattrs"
AUTHOR = "Tin Tvrtkovic <tinchester@gmail.com>"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=12efd5ce6c6c43c4ead370bd15f93560"

DEPENDS += "${PYTHON_PN}-hatch-vcs-native"

inherit pypi python_hatchling

SRC_URI[md5sum] = "7d8d5e75cdf166ffc4ea6aadad70235e"
SRC_URI[sha256sum] = "8028cfe1ff5382df59dd36474a86e02d817b06eaf8af84555441bac915d2ef85"

RDEPENDS:${PN} = "${PYTHON_PN}-attrs"

include ${PYTHON_PN}-package-split.inc
