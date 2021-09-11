SUMMARY = "Backport of functools.lru_cache from Python 3.3"
HOMEPAGE = "https://github.com/jaraco/backports.functools_lru_cache"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7a7126e068206290f3fe9f8d6c713ea6"

PYPI_PACKAGE = "backports.functools_lru_cache"

SRC_URI[md5sum] = "8fed424f30bf9554235aa02997b7574c"
SRC_URI[sha256sum] = "d5ed2169378b67d3c545e5600d363a923b09c456dab1593914935a68ad478271"

DEPENDS += "python-setuptools-scm-native"

inherit setuptools pypi python-backports-init

RDEPENDS_${PN} += "\
    ${PYTHON_PN}-pkgutil \
    ${PYTHON_PN}-pickle \
    ${PYTHON_PN}-threading \
    "

do_install() {
    install -d ${D}${PYTHON_SITEPACKAGES_DIR}/backports
    install ${B}/backports/functools_lru_cache.py ${D}${PYTHON_SITEPACKAGES_DIR}/backports/
}

FILES_${PN} = "${PYTHON_SITEPACKAGES_DIR}/backports/functools_lru_cache.py"

BBCLASSEXTEND = "native nativesdk"
