SUMMARY  = "JavaScript to Python Translator & JavaScript interpreter written in 100% pure Python."
DESCRIPTION = "Translates JavaScript to Python code. Js2Py is able to translate and execute virtually any JavaScript code."
HOMEPAGE = "https://pypi.org/project/Js2Py"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://PKG-INFO;md5=58022ecaa327b9aebde5eca36e593aa8"

SRC_URI = "https://files.pythonhosted.org/packages/50/ca/96809659b66f06f10a65a73023d5a2cdc1f9376f080473ea9397593e2b7b/Js2Py-0.70.tar.gz"
SRC_URI[md5sum] = "1a62150667ac2b2c49a3e2490ee5b40a"
SRC_URI[sha256sum] = "7568b33f6bd15ee8ab1f3655928ed05481a6236ad2acca4598703ae501571661"

S = "${WORKDIR}/Js2Py-${PV}"

inherit setuptools3

include ${PYTHON_PN}-package-split.inc
