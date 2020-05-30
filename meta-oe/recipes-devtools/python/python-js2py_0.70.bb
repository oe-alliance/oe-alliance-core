SUMMARY  = "JavaScript to Python Translator & JavaScript interpreter written in 100% pure Python."
DESCRIPTION = "Translates JavaScript to Python code. Js2Py is able to translate and execute virtually any JavaScript code."
HOMEPAGE = "https://pypi.org/project/Js2Py"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://PKG-INFO;md5=58022ecaa327b9aebde5eca36e593aa8"

SRC_URI = "https://files.pythonhosted.org/packages/50/ca/96809659b66f06f10a65a73023d5a2cdc1f9376f080473ea9397593e2b7b/Js2Py-0.70.tar.gz"
SRC_URI[md5sum] = "1b7de605f8e26cce83be19d2a100652d"
SRC_URI[sha256sum] = "168952e3827198f68eacbf84a7b23c80a55794415291f821ec6c96f3fd9c3253"

S = "${WORKDIR}/Js2Py-${PV}"

inherit setuptools

include ${PYTHON_PN}-package-split.inc
