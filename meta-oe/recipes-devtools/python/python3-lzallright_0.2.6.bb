SUMMARY = "A Python 3.8+ binding for LZ library"
HOMEPAGE = "https://vlaci.github.io/lzallright/"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b7b5988590034c58a9adda7d92503c22"

SRC_URI[md5sum] = "3bfc9ea8a7e0c46830cb07e8cbe7f367"
SRC_URI[sha256sum] = "15917d4e5aadf601b58da0bbf034c5715853fffe843a3ee4b5040ae0e5d954ad"

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