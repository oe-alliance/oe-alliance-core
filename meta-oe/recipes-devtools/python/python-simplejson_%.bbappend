include python-package-split.inc

do_install_append() {

# If things were installed a level down. move them up:

if [ -d ${D}${libdir}/python2.7/site-packages/simplejson-${PV}*/simplejson ]; then
    echo "Moving install up a level"
    mv ${D}${libdir}/python2.7/site-packages/*/* ${D}${libdir}/python2.7/site-packages/
fi
    rm -rf ${D}${libdir}/python2.7/site-packages/simplejson-${PV}*
}
