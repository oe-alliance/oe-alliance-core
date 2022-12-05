FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

PACKAGE_ARCH := "${MACHINE_ARCH}"

SRC_URI:append:sf8008 = " \
    file://libjpeg.so.62.2.0 \
"

SRC_URI:append:sf8008m = " \
    file://libjpeg.so.62.2.0 \
"

SRC_URI:append:sx88v2 = " \
    file://libjpeg.so.8.2.2 \
"

SRC_URI:append:sf8008opt = " \
    file://libjpeg.so.8.2.2 \
"

SRC_URI:append:sx988 = " \
    file://libjpeg.so.8.2.2 \
"

SRC_URI:append:sfx6008 = " \
    file://libjpeg.so.8.2.2 \
"

do_install:append:sf8008() {
    install -d ${D}${libdir}
    install -m 0755 ${WORKDIR}/libjpeg.so.62.2.0 ${D}${libdir}/
}

do_install:append:sf8008m() {
    install -d ${D}${libdir}
    install -m 0755 ${WORKDIR}/libjpeg.so.62.2.0 ${D}${libdir}/
}

do_install:append:sx88v2() {
    install -d ${D}${libdir}
    install -m 0755 ${WORKDIR}/libjpeg.so.8.2.2 ${D}${libdir}/
}

do_install:append:sf8008opt() {
    install -d ${D}${libdir}
    install -m 0755 ${WORKDIR}/libjpeg.so.8.2.2 ${D}${libdir}/
    ln -s libjpeg.so.8.2.2 ${D}${libdir}/libjpeg.so.8
}

do_install:append:sfx6008() {
    install -d ${D}${libdir}
    install -m 0755 ${WORKDIR}/libjpeg.so.8.2.2 ${D}${libdir}/
    ln -s libjpeg.so.8.2.2 ${D}${libdir}/libjpeg.so.8
}

do_install:append:sx988() {
    install -d ${D}${libdir}
    install -m 0755 ${WORKDIR}/libjpeg.so.8.2.2 ${D}${libdir}/
    ln -s libjpeg.so.8.2.2 ${D}${libdir}/libjpeg.so.8
}

INSANE_SKIP:${PN} = "already-stripped"
