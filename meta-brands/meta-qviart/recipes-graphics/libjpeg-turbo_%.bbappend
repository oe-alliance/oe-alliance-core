FILESEXTRAPATHS:prepend := "${THISDIR}/files:"


SRC_URI:append:og2ott4k = " \
    file://libjpeg.so.8.2.2 \
"

SRC_URI:append:og2s4k = " \
    file://libjpeg.so.8.2.2 \
"


do_install:append:og2ott4k() {
    install -d ${D}${libdir}
    install -m 0755 ${WORKDIR}/libjpeg.so.8.2.2 ${D}${libdir}/
    ln -s libjpeg.so.8.2.2 ${D}${libdir}/libjpeg.so.8
}

do_install:append:og2s4k() {
    install -d ${D}${libdir}
    install -m 0755 ${WORKDIR}/libjpeg.so.8.2.2 ${D}${libdir}/
    ln -s libjpeg.so.8.2.2 ${D}${libdir}/libjpeg.so.8
}

INSANE_SKIP:${PN} = "already-stripped 32bit-time"
