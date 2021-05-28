DISTUTILS_INSTALL_ARGS = " \
    --root=${D} \
    --install-lib=${PYTHON_SITEPACKAGES_DIR} \
"

include ${PYTHON_PN}-package-split.inc
