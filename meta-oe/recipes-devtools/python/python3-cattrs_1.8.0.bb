SUMMARY = "Composable complex class support for attrs and dataclasses."
HOMEPAGE = "https://github.com/Tinche/cattrs"
AUTHOR = "Tin Tvrtkovic <tinchester@gmail.com>"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=12efd5ce6c6c43c4ead370bd15f93560"

inherit setuptools3

SRC_URI = "https://files.pythonhosted.org/packages/d3/6f/7f753ab5bff9c2522398437f9b08879b890a2ac4d880654029b9302a0a0b/cattrs-1.8.0.tar.gz"
SRC_URI[md5sum] = "1e61274bd618d9385bd0d31d3e1e3dd5"
SRC_URI[sha256sum] = "5c121ab06a7cac494813c228721a7feb5a6423b17316eeaebf13f5a03e5b0d53"

S = "${WORKDIR}/cattrs-1.8.0"

RDEPENDS:${PN} = "${PYTHON_PN}-attrs"

include ${PYTHON_PN}-package-split.inc
