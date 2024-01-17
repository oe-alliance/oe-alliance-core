SUMMARY = "Google Data APIs Python Client Library"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://README.md;md5=347bb370123782a1b67b40f2596b53c1"
HOMEPAGE = "http://code.google.com/p/gdata-python-client/"

SRC_URI[md5sum] = "bdabfdb150b1e94c848bd1ff8c9bf9aa"
SRC_URI[sha256sum] = "b77301becfb3bf42e9a459169e75e6ff4c20cc7b7e247d4d84988e8c8ac6d898"

S = "${WORKDIR}/gdata-python3-${PV}"

inherit pypi setuptools3

SRC_URI += "file://0001-fix-compile-warning.patch"

deltask do_populate_sysroot

RDEPENDS:${PN} = " \
    ${PYTHON_PN}-json \
    ${PYTHON_PN}-netserver \
    ${PYTHON_PN}-stringold \
    ${PYTHON_PN}-xml \
"
