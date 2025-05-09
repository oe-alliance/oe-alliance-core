SUMMARY = "Extract files from UBI and UBIFS images."
HOMEPAGE = "https://pypi.org/project/ubi-reader"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c6b470a8e9d49cabef4cfcb48750a22f"

SRC_URI[md5sum] = "a7f840661b0b02744f74afd4670792f2"
SRC_URI[sha256sum] = "b2763b85f8b6c68bce592d674aa9e9cadda2939a634f42954518d5c786c60f10"

RDEPENDS:${PN} += "python3-lzallright"

PYPI_PACKAGE = "ubi_reader"

inherit pypi python_poetry_core

FILES:${PN}-src += " \
    ${PYTHON_SITEPACKAGES_DIR}/*.py \
    ${PYTHON_SITEPACKAGES_DIR}/*/*.py \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*.py \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/*.py \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/*/*.py \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/*/*/*.py \
    "