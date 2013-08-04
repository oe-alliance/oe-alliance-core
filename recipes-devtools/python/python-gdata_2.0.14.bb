SUMMARY = "Google Data APIs Python Client Library"
HOMEPAGE = "http://code.google.com/p/gdata-python-client/"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://README.txt;beginline=1;endline=13;md5=7a713fc5eed20ac1904c2efe0b816a33"
RDEPENDS_${PN} = "python-elementtree"
RDEPENDS_${PN}-tests = "${PN}"
PR = "r1"

SRC_URI = "http://gdata-python-client.googlecode.com/files/gdata-${PV}.tar.gz;name=archive"
SRC_URI[archive.md5sum] = "29cf394b4f75aa4fae8c1b59079f2a02"
SRC_URI[archive.sha256sum] = "ba291d2b9d36a0f1b1b31a5a3ac3ba11f1bcce21c915a6ec78d109a43dafb1b0"

S = "${WORKDIR}/gdata-${PV}"

inherit distutils

PACKAGES =+ "${PN}-tests"

FILES_${PN}-tests = "${libdir}/${PYTHON_DIR}/site-packages/gdata/test*"
FILES_${PN} += "${datadir}"
