DISTUTILS_INSTALL_ARGS = " \
    --root=${D} \
    --install-lib=${PYTHON_SITEPACKAGES_DIR} \
"

include python3-package-split.inc
