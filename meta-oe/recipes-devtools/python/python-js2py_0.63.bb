SUMMARY  = "JavaScript to Python Translator & JavaScript interpreter written in 100% pure Python."
DESCRIPTION = "Translates JavaScript to Python code. Js2Py is able to translate and execute virtually any JavaScript code."
HOMEPAGE = "https://pypi.org/project/Js2Py"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://PKG-INFO;md5=1a17f0cf79ec92d305098ed7a15a9824"

SRC_URI = "https://files.pythonhosted.org/packages/90/ef/c0800fcfa568ee4baa95a7ceec5a33178bac86e6643857f30cf3ddb78933/Js2Py-${PV}.tar.gz"

S = "${WORKDIR}/Js2Py-${PV}"

inherit setuptools

SRC_URI[md5sum] = "7da890d5d1f049edc71a03b8166a42f1"
SRC_URI[sha256sum] = "5562351d4fdc5b7773390f880585da7550e3a6072d0df854c2f44bd41314d0b4"

include python-package-split.inc
