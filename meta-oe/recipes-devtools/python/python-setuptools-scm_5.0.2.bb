SUMMARY = "the blessed package to manage your versions by scm tags"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://PKG-INFO;beginline=8;endline=8;md5=8227180126797a0148f94f483f3e1489"

SRC_URI[md5sum] = "8ddd44e0cd3a243350fe709024ec7224"
SRC_URI[sha256sum] = "83a0cedd3449e3946307811a4c7b9d89c4b5fd464a2fb5eeccd0a5bb158ae5c8"

PYPI_PACKAGE = "setuptools_scm"

inherit pypi setuptools

RDEPENDS_${PN}_class-target = "python-py python-setuptools python-debugger python-json python-toml "
RDEPENDS_${PN}_class-native = "python-setuptools-native python-toml-native"

RDEPENDS_${PN} += "python-argparse"

BBCLASSEXTEND = "native"
include python-package-split.inc
