SUMMARY = "Module for manipulating ID3 (v1 + v2) tags in Python"
SECTION = "devel/python"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"
SRCNAME = "mutagen"
PR = "r1"

DEPENDS = "python"
RDEPENDS_${PN} = "python-shell"

SRC_URI = "http://mutagen.googlecode.com/files/mutagen-${PV}.tar.gz \
    file://patch.diff \
"

SRC_URI[md5sum] = "0c2cd954e4bacd79fadd45afc4acce4c"
SRC_URI[sha256sum] = "30b6147baf59ab3609939acf49a1a1c73b15d8b1c637a01bfee89da7feea0d6c"

S = "${WORKDIR}/mutagen-${PV}"

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

