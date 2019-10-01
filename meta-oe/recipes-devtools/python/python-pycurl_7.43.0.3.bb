SUMMARY = "A Python interface to libcurl"
HOMEPAGE = "http://pycurl.io"
SECTION = "devel/python"
LICENSE = "LGPLv2.1+ | MIT"
LIC_FILES_CHKSUM = "file://README.rst;beginline=170;endline=186;md5=c9e76cf3dfdae0ac62428d41192d6b26 \
                    file://COPYING-LGPL;md5=4fbd65380cdd255951079008b364516c \
                    file://COPYING-MIT;md5=2df767ed35d8ea83de4a93feb55e7815"

DEPENDS = "curl ${PYTHON_PN}"
RDEPENDS_${PN} = "curl ${PYTHON_PN}"

SRC_URI = "\
        https://files.pythonhosted.org/packages/ac/b3/0f3979633b7890bab6098d84c84467030b807a1e2b31f5d30103af5a71ca/pycurl-${PV}.tar.gz \
        file://no-static-link.patch \
"

SRC_URI[md5sum] = "f0ed4c805e8bec734990e2e0ee78568e"
SRC_URI[sha256sum] = "6f08330c5cf79fa8ef68b9912b9901db7ffd34b63e225dce74db56bb21deda8e"

S = "${WORKDIR}/pycurl-${PV}"

inherit distutils

BBCLASSEXTEND = "native"

# Ensure the docstrings are generated as make clean will remove them
do_compile_prepend() {
    ${STAGING_BINDIR_NATIVE}/${PYTHON_PN}-native/${PYTHON_PN} setup.py docstrings
}

do_install_append() {
    rm -rf ${D}${datadir}/share
}

include python-package-split.inc
