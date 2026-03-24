SUMMARY = "Module for text manipulation"
HOMEPAGE = "https://github.com/jaraco/jaraco.text"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=2e21c3f75eb8d9427c8a611a8e83e9d6"
DEPENDS += "python3-setuptools-scm-native python3-coherent-licensed-native"

PYPI_PACKAGE = "jaraco_text"

SRC_URI[md5sum] = "651066dde8eb1d40523454fd88d280e5"
SRC_URI[sha256sum] = "194e386aa5b15a6616019df87a6b29c00fd3c9c8b0475731b64633ca7afd495b"

inherit pypi python_setuptools_build_meta

FILES:${PN}-src += " \
    ${PYTHON_SITEPACKAGES_DIR}/*.py \
    ${PYTHON_SITEPACKAGES_DIR}/*/*.py \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*.py \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/*.py \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/*/*.py \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/*/*/*.py \
    "