DESCRIPTION = "Python 2 and 3 compatibility utilities."
HOMEPAGE = "http://pypi.python.org/pypi/six/1.3.0"
SECTION = "devel/python"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=aa39e09066d6ba3a8c8b2db7cbd8bd70"

PR = "r1"

SRC_URI = "http://pypi.python.org/packages/source/s/six/six-${PV}.tar.gz"

SRC_URI[md5sum] = "ec47fe6070a8a64c802363d2c2b1e2ee"
SRC_URI[sha256sum] = "d59793f9b255bd00de800b97f9a50cce4fc8a44c205f7defa5bb7d691d13b852"

S = "${WORKDIR}/six-${PV}"

inherit distutils

FILES_${PN}-tests = " \
  ${libdir}/${PYTHON_DIR}/site-packages/*/tests \
  ${libdir}/${PYTHON_DIR}/site-packages/*/*/tests \
"

PACKAGES =+ "${PN}-tests"
