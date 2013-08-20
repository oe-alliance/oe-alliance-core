PRINC = "1"

PACKAGES =+ " ${PN}-src ${PN}-tests"
RDEPENDS_{PN}-src = "${PN}"
FILES_${PN}-tests = " \
    ${libdir}/${PYTHON_DIR}/site-packages/*/Tests \
    "
FILES_${PN}-src = " \
    ${libdir}/${PYTHON_DIR}/site-packages/*/*.py \
    ${libdir}/${PYTHON_DIR}/site-packages/*/*/*.py \
    ${libdir}/${PYTHON_DIR}/site-packages/*/*/*/*.py \
    ${libdir}/${PYTHON_DIR}/site-packages/*/*/*/*/*.py \
    "
