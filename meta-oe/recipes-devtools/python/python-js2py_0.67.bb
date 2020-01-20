SUMMARY  = "JavaScript to Python Translator & JavaScript interpreter written in 100% pure Python."
DESCRIPTION = "Translates JavaScript to Python code. Js2Py is able to translate and execute virtually any JavaScript code."
HOMEPAGE = "https://pypi.org/project/Js2Py"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://PKG-INFO;md5=2052cf2e4726d6f7985f428a9ab70cd5"

SRC_URI = "https://files.pythonhosted.org/packages/ba/75/2fdf4fc9403f1c683f1ae138ebef62bb0a1e388b7aacf21c3cd35e998507/Js2Py-${PV}.tar.gz"
SRC_URI[md5sum] = "1b7de605f8e26cce83be19d2a100652d"
SRC_URI[sha256sum] = "168952e3827198f68eacbf84a7b23c80a55794415291f821ec6c96f3fd9c3253"

S = "${WORKDIR}/Js2Py-${PV}"

inherit setuptools

include python-package-split.inc
