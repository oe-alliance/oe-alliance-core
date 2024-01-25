SUMMARY  = "JavaScript to Python Translator & JavaScript interpreter written in 100% pure Python."
DESCRIPTION = "Translates JavaScript to Python code. Js2Py is able to translate and execute virtually any JavaScript code."
HOMEPAGE = "https://pypi.org/project/Js2Py"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://PKG-INFO;md5=8d0781267244a59a92ee444c65e6b8c4"

PYPI_PACKAGE = "Js2Py"

SRC_URI[md5sum] = "92828a45dd6a8a20843500305a57ec78"
SRC_URI[sha256sum] = "39f3a6aa8469180efba3c8677271df27c31332fd1b471df1af2af58b87b8972f"

inherit pypi setuptools3_legacy

include ${PYTHON_PN}-package-split.inc
