SUMMARY  = "A module to work with countries and languages"
HOMEPAGE = "https://github.com/Diaoul/babelfish"
SECTION = "devel/python"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=6570714fbba8ff492626c8753620c54d"

SRC_URI[md5sum] = "a3ef27e5b12c795d48aa6fa4005d2826"
SRC_URI[sha256sum] = "decb67a4660888d48480ab6998309837174158d0f1aa63bebb1c2e11aab97aab"

S = "${WORKDIR}/babelfish-${PV}"

inherit pypi python_poetry_core

FILES:${PN}-src += " \
    ${PYTHON_SITEPACKAGES_DIR}/*.py \
    ${PYTHON_SITEPACKAGES_DIR}/*/*.py \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*.py \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/*.py \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/*/*.py \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/*/*/*.py \
    "

FILES:${PN}-doc += " \
    ${PYTHON_SITEPACKAGES_DIR}/*-info \
    ${PYTHON_SITEPACKAGES_DIR}/*/*-info \
    ${PYTHON_SITEPACKAGES_DIR}/*-INFO \
    ${PYTHON_SITEPACKAGES_DIR}/*/*-INFO \
    ${PYTHON_SITEPACKAGES_DIR}/*-safe \
    ${PYTHON_SITEPACKAGES_DIR}/*/*-safe \
    ${PYTHON_SITEPACKAGES_DIR}/doc \
    ${PYTHON_SITEPACKAGES_DIR}/*/doc \
    ${PYTHON_SITEPACKAGES_DIR}/LICENSE \
    ${PYTHON_SITEPACKAGES_DIR}/README \
    "