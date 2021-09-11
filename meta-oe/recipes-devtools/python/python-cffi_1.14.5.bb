SUMMARY = "Foreign Function Interface for Python calling C code"
HOMEPAGE = "http://cffi.readthedocs.org/"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=5677e2fdbf7cdda61d6dd2b57df547bf"
DEPENDS += "libffi ${PYTHON_PN}-pycparser"

SRC_URI[md5sum] = "272cb183bf0365530e3c0d8f446cd89d"
SRC_URI[sha256sum] = "fd78e5fee591709f32ef6edb9a015b4aa1a5022598e36227500c8f4e02328d9c"

inherit pypi setuptools

RDEPENDS_${PN}_class-target = " \
    ${PYTHON_PN}-ctypes \
    ${PYTHON_PN}-io \
    ${PYTHON_PN}-pycparser \
    ${PYTHON_PN}-shell \
    ${PYTHON_PN}-subprocess \
"

BBCLASSEXTEND = "native nativesdk"

include python-package-split.inc
