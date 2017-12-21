SUMMARY = "Portable network interface information."
HOMEPAGE = "https://pypi.python.org/pypi/netifaces"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit setuptools pypi

include python-package-split.inc

SRC_URI[md5sum] = "1d424cb5ef52907c5ab913011122a98b"
SRC_URI[sha256sum] = "0c4da523f36d36f1ef92ee183f2512f3ceb9a9d2a45f7d19cda5a42c6689ebe0"
