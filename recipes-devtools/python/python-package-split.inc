PRINC = "4"

inherit python-dir

PACKAGES =+ " ${PN}-src ${PN}-tests"
RDEPENDS_{PN}-src = "${PN}"

FILES_${PN}-src = " \
    ${PYTHON_SITEPACKAGES_DIR}/*.py \
    ${PYTHON_SITEPACKAGES_DIR}/*/*.py \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*.py \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/*.py \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/*/*.py \
    "

FILES_${PN}-tests += " \
    ${PYTHON_SITEPACKAGES_DIR}/test \
    ${PYTHON_SITEPACKAGES_DIR}/tests \
    ${PYTHON_SITEPACKAGES_DIR}/*/test \
    ${PYTHON_SITEPACKAGES_DIR}/*/tests \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/test \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/tests \
"

# some txt files which should go into -doc
FILES_${PN}-doc += " \
    ${PYTHON_SITEPACKAGES_DIR}/*-info \
    ${PYTHON_SITEPACKAGES_DIR}/*/*-info \
    "

FILES_${PN}-dbg += " \
    ${PYTHON_SITEPACKAGES_DIR}/*/.debug \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/.debug \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/.debug \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/*/.debug \
    "
