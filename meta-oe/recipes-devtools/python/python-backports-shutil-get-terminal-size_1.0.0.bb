SUMMARY = "A backport of the get_terminal_size function from Python 3.3’s shutil."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f3168ae4710d8f8c93f1b937983ae0dc"

inherit pypi setuptools python-backports-init

PYPI_PACKAGE = "backports.shutil_get_terminal_size"

PACKAGES = "${PN}"

SRC_URI[md5sum] = "03267762480bd86b50580dc19dff3c66"
SRC_URI[sha256sum] = "713e7a8228ae80341c70586d1cc0a8caa5207346927e23d09dcbcaf18eadec80"
