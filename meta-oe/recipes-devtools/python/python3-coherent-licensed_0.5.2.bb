SUMMARY = "License management tooling for Coherent System and skeleton projects"
HOMEPAGE = "https://github.com/coherent-oss/coherent.licensed"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1aeae65f25a15b1e46d4381f2f094e0a"

PYPI_PACKAGE = "coherent_licensed"

SRC_URI[md5sum] = "5c943470c62e4a7825e783a53816fd18"
SRC_URI[sha256sum] = "d8071403ce742d3ac3592ddc4fb7057a46caffb415b928b4d52802e5f208416d"

inherit pypi python_setuptools_build_meta

BBCLASSEXTEND = "native nativesdk"