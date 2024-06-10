FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append:ip8 = " \
    file://libjpeg.so.8.2.2 \
"

do_install:append:ip8() {
    install -d ${D}${libdir}
    install -m 0755 ${UNPACKDIR}/libjpeg.so.8.2.2 ${D}${libdir}/
    ln -s libjpeg.so.8.2.2 ${D}${libdir}/libjpeg.so.8
}

INSANE_SKIP:${PN} = "already-stripped 32bit-time"
