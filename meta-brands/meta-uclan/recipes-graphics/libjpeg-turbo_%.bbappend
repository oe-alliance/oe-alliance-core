FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append:ustym4kpro = " \
     file://libjpeg.so.62.2.0 \
"

SRC_URI:append:ustym4kottpremium = " \
    file://libjpeg.so.8.2.2 \
"

SRC_URI:append:ustym4ks2ottx = " \
    file://libjpeg.so.8.2.2 \
"

do_install:append:ustym4kpro() {
    install -d ${D}${libdir}
    install -m 0755 ${WORKDIR}/libjpeg.so.62.2.0 ${D}${libdir}/
}

do_install:append:ustym4kottpremium() {
    install -d ${D}${libdir}
    install -m 0755 ${WORKDIR}/libjpeg.so.8.2.2 ${D}${libdir}/
    ln -s libjpeg.so.8.2.2 ${D}${libdir}/libjpeg.so.8
}

do_install:append:ustym4ks2ottx() {
    install -d ${D}${libdir}
    install -m 0755 ${WORKDIR}/libjpeg.so.8.2.2 ${D}${libdir}/
    ln -s libjpeg.so.8.2.2 ${D}${libdir}/libjpeg.so.8
}
