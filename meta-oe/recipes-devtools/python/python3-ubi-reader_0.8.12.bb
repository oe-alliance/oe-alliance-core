SUMMARY = "Extract files from UBI and UBIFS images."
HOMEPAGE = "https://pypi.org/project/ubi-reader"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c6b470a8e9d49cabef4cfcb48750a22f"

SRC_URI[md5sum] = "398fe3d5352da0dceadf95f68f2de382"
SRC_URI[sha256sum] = "12e7f1b5da5082c50b2b316efdac9375de5870e077bc2d984f4c0f9e1112b640"

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