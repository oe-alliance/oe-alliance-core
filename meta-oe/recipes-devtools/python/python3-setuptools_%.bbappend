PR .= ".1"

FILES:${PN}-src += " \
    ${PYTHON_SITEPACKAGES_DIR}/*.py \
    ${PYTHON_SITEPACKAGES_DIR}/*/*.py \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*.py \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/*.py \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/*/*.py \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/*/*/*.py \
    "
