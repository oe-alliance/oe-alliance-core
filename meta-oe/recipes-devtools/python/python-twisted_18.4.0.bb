inherit pypi setuptools
require python-twisted.inc

RDEPENDS_${PN}-core += "${PYTHON_PN}-contextlib python-incremental python-constantly python-service-identity python-hyperlink"

SRC_URI += " \
           file://fix-writing-after-channel-is-closed.patch \
"

PR = "r1"

FILES_${PN}-dbg += " \
    ${libdir}/${PYTHON_DIR}/site-packages/twisted/*.egg-info \
    ${libdir}/${PYTHON_DIR}/site-packages/twisted/*/*/test \
"

