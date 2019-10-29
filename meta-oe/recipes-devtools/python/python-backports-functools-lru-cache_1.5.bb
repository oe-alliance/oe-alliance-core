SUMMARY = "Backport of functools.lru_cache from Python 3.3"
HOMEPAGE = "https://github.com/jaraco/backports.functools_lru_cache"
AUTHOR = "Jason R. Coombs <jaraco@jaraco.com>"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a33f38bbf47d48c70fe0d40e5f77498e"

DEPENDS = "python-setuptools-scm-native"

SRC_URI = "https://files.pythonhosted.org/packages/57/d4/156eb5fbb08d2e85ab0a632e2bebdad355798dece07d4752f66a8d02d1ea/backports.functools_lru_cache-${PV}.tar.gz"
SRC_URI[md5sum] = "20f53f54cd3f04b3346ce75a54959754"
SRC_URI[sha256sum] = "9d98697f088eb1b0fa451391f91afb5e3ebde16bbdb272819fd091151fda4f1a"

S = "${WORKDIR}/backports.functools_lru_cache-${PV}"

inherit setuptools

do_install_append() {
    # python-lzma already provides __init__.py(o) files
    rm -rf ${D}${libdir}/${PYTHON_DIR}/site-packages/backports/__init__.py*
}
