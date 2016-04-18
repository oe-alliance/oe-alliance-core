SUMMARY = "A Python crypto and SSL toolkit"
SECTION = "devel/python"
HOMEPAGE = "http://chandlerproject.org/bin/view/Projects/MeTooCrypto"
PRIORITY = "optional"
DEPENDS = "openssl swig-native"
LICENSE = "BSD"
SRCNAME = "M2Crypto"
PR = "ml13"

require conf/license/license-gplv2.inc

SRC_URI = "\
  http://pypi.python.org/packages/source/M/${SRCNAME}/${SRCNAME}-${PV}.tar.gz \
  file://m2crypto-fixes.patch \
  file://install.patch \
  "

S = "${WORKDIR}/${SRCNAME}-${PV}"

inherit setuptools

export STAGING_DIR
export STAGING_INCDIR
export STAGING_LIBDIR

do_configure_prepend() {
    sed -i -e "s/opensslconf\./opensslconf-32\./" SWIG/_ec.i
    sed -i -e "s/opensslconf\./opensslconf-32\./" SWIG/_evp.i
}

do_configure_cube_prepend() {
}

SRC_URI[md5sum] = "f93d8462ff7646397a9f77a2fe602d17"
SRC_URI[sha256sum] = "25b94498505c2d800ee465db0cc1aff097b1615adc3ac042a1c85ceca264fc0a"

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

