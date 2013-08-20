DESCRIPTION = "General purpose components extending the circuits framework."
HOMEPAGE = "https://pypi.python.org/pypi/circuits-brickss"
SECTION = "devel/python"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://README;md5=85280f2861fb3a0e5ff5e5873bf947a3"

SRC_URI = "https://pypi.python.org/packages/source/c/circuits-bricks/circuits-bricks-${PV}.tar.gz"

PR = "r10"

S = "${WORKDIR}/circuits-bricks-${PV}"

inherit distutils

SRC_URI[md5sum] = "49c5afa93b01f9ffa30404c75a382877"
SRC_URI[sha256sum] = "0854a14de8cdf919e0451f99697222906f743c5867d7830248bd575a3655d814"

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

