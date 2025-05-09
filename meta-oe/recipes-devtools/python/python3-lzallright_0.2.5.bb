SUMMARY = "A Python 3.8+ binding for LZ library"
HOMEPAGE = "https://vlaci.github.io/lzallright/"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b7b5988590034c58a9adda7d92503c22"

SRC_URI[md5sum] = "2c92b9b7d4c13511980a4108381ffcf6"
SRC_URI[sha256sum] = "e467cb21b58669c1d2f00ba714230ec8133c302e52006038186098ffd02fafd7"

SRC_URI += "file://build-rs.patch"

require ${BPN}-crates.inc

inherit pypi python_setuptools3_rust cargo-update-recipe-crates python_maturin

FILES:${PN}-src += " \
    ${PYTHON_SITEPACKAGES_DIR}/*.py \
    ${PYTHON_SITEPACKAGES_DIR}/*/*.py \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*.py \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/*.py \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/*/*.py \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/*/*/*.py \
    "