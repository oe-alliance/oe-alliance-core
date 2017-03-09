include python-package-split.inc

do_install_append() {

# If things were installed a level down. move them up:

if [ -d ${D}/usr/lib/python2.7/site-packages/simplejson-${PV}*/simplejson ]; then
    echo "Moving install up a level"
    mv ${D}/usr/lib/python2.7/site-packages/*/* ${D}/usr/lib/python2.7/site-packages/
fi
    rm -rf ${D}/usr/lib/python2.7/site-packages/simplejson-${PV}*
}
