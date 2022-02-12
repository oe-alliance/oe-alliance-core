FILESEXTRAPATHS_prepend := "${THISDIR}/files:"


SRC_URI_append_og2ott4k = " \
    file://libjpeg.so.8.2.2 \
"


do_install_append_og2ott4k() {
    install -d ${D}${libdir}
    install -m 0755 ${WORKDIR}/libjpeg.so.8.2.2 ${D}${libdir}/
    ln -s libjpeg.so.8.2.2 ${D}${libdir}/libjpeg.so.8
}