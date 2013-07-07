DESCRIPTION = "Extensions to the standard Python datetime module."
HOMEPAGE = "http://pypi.python.org/pypi/python-dateutil"
SECTION = "devel/python"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=badb0fe9d510b2bd9dce482a5435b585"

PR = "r3"

DEPENDS_${PN} = "python-six"
RDEPENDS_${PN} = "python-six"

SRC_URI = "http://pypi.python.org/packages/source/p/python-dateutil/python-dateutil-${PV}.tar.gz"

SRC_URI[md5sum] = "1534bb15cf311f07afaa3aacba1c028b"
SRC_URI[sha256sum] = "4c44ec3f9ff057b8c7b4c78beca5fdd8710600ea9a1df42f31bfcbae2f059dee"

S = "${WORKDIR}/python-dateutil-${PV}"

inherit distutils

FILES_${PN}-tests = " \
  ${libdir}/${PYTHON_DIR}/site-packages/*/tests \
  ${libdir}/${PYTHON_DIR}/site-packages/*/*/tests \
"

PACKAGES =+ "${PN}-tests"
