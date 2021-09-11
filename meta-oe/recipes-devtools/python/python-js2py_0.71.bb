SUMMARY  = "JavaScript to Python Translator & JavaScript interpreter written in 100% pure Python."
DESCRIPTION = "Translates JavaScript to Python code. Js2Py is able to translate and execute virtually any JavaScript code."
HOMEPAGE = "https://pypi.python.org/pypi/Js2Py"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://PKG-INFO;md5=05862b424b2fff0810da47d7e70e7185"

PR = "r0"

SRC_URI = "https://files.pythonhosted.org/packages/36/61/3c6a930ffcee1026a495406dd3c156ae400cf7aa14d26bfbbd0df638b4b9/Js2Py-${PV}.tar.gz"

S = "${WORKDIR}/Js2Py-${PV}"

inherit setuptools

SRC_URI[md5sum] = "451a084bec40bd43d6932a6bb1af1757"
SRC_URI[sha256sum] = "a41b1009dd1498ae7d436bfa5ac952a08ca92a4bb9e31dca6e8bb966b49f7fce"

include python-package-split.inc
