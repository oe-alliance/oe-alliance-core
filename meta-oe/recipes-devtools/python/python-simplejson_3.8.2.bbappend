include python-package-split.inc

do_install_append() {
    mv ${D}/usr/lib/python2.7/site-packages/*/* ${D}/usr/lib/python2.7/site-packages/
    rm -rf ${D}/usr/lib/python2.7/site-packages/simplejson-${PV}*
}