SUMMARY  = "RAR archive reader for Python"
HOMEPAGE = "https://github.com/markokr/rarfile"
SECTION = "devel/python"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=2f31e224cbf0c29cb6c55f2bae0e165f"

SRC_URI[md5sum] = "35940bb31c831b49c963c48280416873"
SRC_URI[sha256sum] = "db60b3b5bc1c4bdeb941427d50b606d51df677353385255583847639473eda48"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
