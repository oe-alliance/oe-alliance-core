RDEPENDS_${PN}-core += "python-incremental python-constantly python-service-identity python-hyperlink"

FILES_${PN}-dbg += " \
    ${libdir}/${PYTHON_DIR}/site-packages/twisted/*.egg-info \
    ${libdir}/${PYTHON_DIR}/site-packages/twisted/*/*/test \
"
