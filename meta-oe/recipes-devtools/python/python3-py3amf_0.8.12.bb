SUMMARY = "AMF support for Python"
HOMEPAGE = "https://github.com/StdCarrot/Py3AMF"
AUTHOR = "The Py3AMF Project <yhbu@stdc.so>"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=f078cf548d49a50c4b44150d5a30585a"

# provide python3-pyamf for enigma2-oe-alliance-plugins
PROVIDES += "python3-pyamf"
RPROVIDES:${PN} += "python3-pyamf"

SRCREV = "2fe5a4a52c4c8fc432038362c866ba1211b74905"

SRC_URI = "git://github.com/StdCarrot/Py3AMF.git;protocol=https;branch=master"

S = "${WORKDIR}/git"

inherit setuptools3_legacy

include ${PYTHON_PN}-package-split.inc
