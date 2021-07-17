SUMMARY = "Backport of functools.lru_cache from Python 3.3"
HOMEPAGE = "https://github.com/jaraco/backports.functools_lru_cache"
AUTHOR = "Jason R. Coombs <jaraco@jaraco.com>"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a33f38bbf47d48c70fe0d40e5f77498e"

PYPI_PACKAGE = "backports.functools_lru_cache"

DEPENDS = "python-setuptools-scm-native"

RDEPENDS_${PN} += "\
    ${PYTHON_PN}-pkgutil \
    ${PYTHON_PN}-pickle \
    ${PYTHON_PN}-threading \
    "

SRC_URI[md5sum] = "20f53f54cd3f04b3346ce75a54959754"
SRC_URI[sha256sum] = "9d98697f088eb1b0fa451391f91afb5e3ebde16bbdb272819fd091151fda4f1a"


inherit setuptools pypi python-backports-init

do_install() {
    install -d ${D}${PYTHON_SITEPACKAGES_DIR}/backports
    install ${B}/backports/functools_lru_cache.py ${D}${PYTHON_SITEPACKAGES_DIR}/backports/
}

FILES_${PN} = "${PYTHON_SITEPACKAGES_DIR}/backports/functools_lru_cache.py"

BBCLASSEXTEND = "native nativesdk"
