do_install_append() {

# If things were installed a level down. move them up:

if [ -d ${D}/usr/lib/${PYTHON_DIR}/site-packages/simplejson-${PV}*/simplejson ]; then
    echo "Moving install up a level"
    mv ${D}/usr/lib/${PYTHON_DIR}/site-packages/*/* ${D}/usr/lib/${PYTHON_DIR}/site-packages/
fi
    rm -rf ${D}/usr/lib/${PYTHON_DIR}/site-packages/simplejson-${PV}*
}

include ${PYTHON_PN}-package-split.inc
