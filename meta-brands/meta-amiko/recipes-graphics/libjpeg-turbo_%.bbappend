FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append:viper4k = " \
     file://libjpeg.so.62.2.0 \
"

do_install:append:viper4k() {
        install -d ${D}${libdir}
        install -m 0755 ${WORKDIR}/libjpeg.so.62.2.0 ${D}${libdir}/
}

INSANE_SKIP:${PN} = "already-stripped 32bit-time"
