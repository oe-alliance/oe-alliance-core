FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append_ustym4kpro = " \
     file://libjpeg.so.62.2.0 \
"

do_install:append_ustym4kpro() {
        install -d ${D}${libdir}
        install -m 0755 ${WORKDIR}/libjpeg.so.62.2.0 ${D}${libdir}/
}
