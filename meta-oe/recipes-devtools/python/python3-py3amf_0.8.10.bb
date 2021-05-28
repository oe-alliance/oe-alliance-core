SUMMARY = "AMF support for Python"
HOMEPAGE = "https://github.com/StdCarrot/Py3AMF"
AUTHOR = "The Py3AMF Project <yhbu@stdc.so>"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=f078cf548d49a50c4b44150d5a30585a"

# provide python3-pyamf for enigma2-oe-alliance-plugins
PROVIDES += "python3-pyamf"
RPROVIDES_${PN} += "python3-pyamf"

SRC_URI = "https://files.pythonhosted.org/packages/f4/f5/d2fb7c5ee1d3e296a328a0205bcf0cce78b57f0a1c822f7e5eadacbe83e2/Py3AMF-0.8.10.tar.gz"
SRC_URI[md5sum] = "f6b64ab8da58ca1657890f6ae87ca9cc"
SRC_URI[sha256sum] = "6dac2d34a09daf5351e654e8cdc3026b3560a6db498c17cdcc84322b3149952c"

S = "${WORKDIR}/Py3AMF-0.8.10"

inherit setuptools3

include ${PYTHON_PN}-package-split.inc
