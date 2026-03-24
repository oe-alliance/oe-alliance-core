SUMMARY = "Extract files from UBI and UBIFS images."
HOMEPAGE = "https://pypi.org/project/ubi-reader"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c6b470a8e9d49cabef4cfcb48750a22f"

SRC_URI[md5sum] = "8a3788d8ec51203e645745a36b829fef"
SRC_URI[sha256sum] = "1ace0429743ff07b5464d349088cd8869763c13dedf02393c72cbfb5e9dfff74"

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