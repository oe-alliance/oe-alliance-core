PRINC = "1"

PACKAGES =+ " ${PN}-src"
RDEPENDS_{PN}-src = "${PN}"
FILES_${PN}-src = " \
    ${libdir}/${PYTHON_DIR}/site-packages/*/*.py \
    ${libdir}/${PYTHON_DIR}/site-packages/*/*/*.py \
    ${libdir}/${PYTHON_DIR}/site-packages/*/*/*/*.py \
    ${libdir}/${PYTHON_DIR}/site-packages/*/*/*/*/*.py \
    "
