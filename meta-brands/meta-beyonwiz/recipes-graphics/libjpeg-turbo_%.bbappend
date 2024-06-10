FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append:beyonwizv2 = " \
     file://libjpeg.so.62.2.0 \
"

do_install:append:beyonwizv2() {
        install -d ${D}${libdir}
        install -m 0755 ${UNPACKDIR}/libjpeg.so.62.2.0 ${D}${libdir}/
}

INSANE_SKIP:${PN} = "already-stripped 32bit-time"
