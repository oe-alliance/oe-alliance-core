SUMMARY = "Python Levenshtein"
HOMEPAGE = "http://github.com/joncasdam/python-Levenshtein"
SECTION = "devel/python"
DEPENDS = "${PYTHON_PN}"
LICENSE = "LGPLv2+"
LIC_FILES_CHKSUM = " file://COPYING;md5=24b9569831c46d4818450b55282476b4"

SRC_URI = "https://files.pythonhosted.org/packages/source/p/python-Levenshtein/python-Levenshtein-${PV}.tar.gz"

SRC_URI[md5sum] = "e8cde197d6d304bbdc3adae66fec99fb"
SRC_URI[sha256sum] = "033a11de5e3d19ea25c9302d11224e1a1898fe5abd23c61c7c360c25195e3eb1"

S = "${WORKDIR}/python-Levenshtein-${PV}"

inherit setuptools3

include ${PYTHON_PN}-package-split.inc
