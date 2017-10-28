SUMMARY  = "JavaScript to Python Translator & JavaScript interpreter written in 100% pure Python."
DESCRIPTION = "Translates JavaScript to Python code. Js2Py is able to translate and execute virtually any JavaScript code."
HOMEPAGE = "https://pypi.python.org/pypi/Js2Py"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://PKG-INFO;md5=0e657c467f9c43e1e317b1ff5bef6c80"

PR = "r0"

SRC_URI = "https://pypi.python.org/packages/52/f8/e0d81d2a1db4025fa6dfa2e0a63f7234d958e11a0497e58c2ff15394c5cb/Js2Py-${PV}.tar.gz"

S = "${WORKDIR}/Js2Py-${PV}"

inherit setuptools

SRC_URI[md5sum] = "c40b07dc7e8e51d268a70146e32b0603"
SRC_URI[sha256sum] = "3bdc8c57518a21cec2cb97a922ecaeb826471d5e13de45a63a91a2b16ad3a1ac"

include python-package-split.inc
