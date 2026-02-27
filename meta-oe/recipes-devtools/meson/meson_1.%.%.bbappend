FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://c1db93be859b360b64fe20d49a286278b56df64e.patch"


# for importlib patch
# add dummy __pycache__ files to make rm happy later...
do_install:prepend() {
    mkdir -p ${D}${libdir}/python${PYTHON_MAJMIN}/site-packages/mesonbuild/dependencies/__pycache__
    touch ${D}${libdir}/python${PYTHON_MAJMIN}/site-packages/mesonbuild/dependencies/__pycache__/mpi.cpython
}
