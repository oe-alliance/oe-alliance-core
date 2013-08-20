DESCRIPTION = "World timezone definitions, modern and historical."
HOMEPAGE = "https://pypi.python.org/pypi/pytz"
SECTION = "devel/python"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=22b38951eb857cf285a4560a914b7cd6"

PR = "r1"

SRC_URI = "http://pypi.python.org/packages/source/p/pytz/pytz-${PV}.tar.gz"

SRC_URI[md5sum] = "64dd31f3f731a5adf9754c32e00d46c3"
SRC_URI[sha256sum] = "0797293d12ca2f7e5dd5bc0807f521f39d5b60a2c347c48f8362f9fd0174af3f"

S = "${WORKDIR}/pytz-${PV}"

inherit distutils

PACKAGES =+ " ${PN}-src ${PN}-tests"
RDEPENDS_{PN}-src = "${PN}"
FILES_${PN}-src = " \
    ${PYTHON_SITEPACKAGES_DIR}/*/*.py \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*.py \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/*.py \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/*/*.py \
    "

FILES_${PN}-tests = " \
  ${PYTHON_SITEPACKAGES_DIR}/*/test \
  ${PYTHON_SITEPACKAGES_DIR}/*/*/test \
"

# some txt files which should go into -doc
FILES_${PN}-doc += " \
    ${PYTHON_SITEPACKAGES_DIR}/*-info \
    ${PYTHON_SITEPACKAGES_DIR}/*/*-info \
    "

