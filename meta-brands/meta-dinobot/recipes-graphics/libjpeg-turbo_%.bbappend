FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

PACKAGE_ARCH := "${MACHINE_ARCH}"

SRC_URI:append:u5 = " \
    file://libjpeg.so.62.3.0.a15 \
"

do_install:append:u5() {
    install -d ${D}${libdir}
    install -m 0755 ${WORKDIR}/libjpeg.so.62.3.0.a15 ${D}${libdir}/libjpeg.so.62.3.0
}

SRC_URI:append:u5pvr = " \
    file://libjpeg.so.62.3.0.a15 \
"

do_install:append:u5pvr() {
    install -d ${D}${libdir}
    install -m 0755 ${WORKDIR}/libjpeg.so.62.3.0.a15 ${D}${libdir}/libjpeg.so.62.3.0
}

SRC_URI:append:u41 = " \
    file://libjpeg.so.62.3.0.a9 \
"

do_install:append:u41() {
    install -d ${D}${libdir}
    install -m 0755 ${WORKDIR}/libjpeg.so.62.3.0.a9 ${D}${libdir}/libjpeg.so.62.3.0
}

SRC_URI:append:u42 = " \
    file://libjpeg.so.62.3.0.a9 \
"

do_install:append:u42() {
    install -d ${D}${libdir}
    install -m 0755 ${WORKDIR}/libjpeg.so.62.3.0.a9 ${D}${libdir}/libjpeg.so.62.3.0
}

SRC_URI:append:u43 = " \
    file://libjpeg.so.62.3.0.a9 \
"

do_install:append:u43() {
    install -d ${D}${libdir}
    install -m 0755 ${WORKDIR}/libjpeg.so.62.3.0.a9 ${D}${libdir}/libjpeg.so.62.3.0
}

SRC_URI:append:u44 = " \
    file://libjpeg.so.62.3.0.a9 \
"

do_install:append:u44() {
    install -d ${D}${libdir}
    install -m 0755 ${WORKDIR}/libjpeg.so.62.3.0.a9 ${D}${libdir}/libjpeg.so.62.3.0
}

SRC_URI:append:u45 = " \
    file://libjpeg.so.62.3.0.a9 \
"

do_install:append:u45() {
    install -d ${D}${libdir}
    install -m 0755 ${WORKDIR}/libjpeg.so.62.3.0.a9 ${D}${libdir}/libjpeg.so.62.3.0
}

SRC_URI:append:u51 = " \
    file://libjpeg.so.62.3.0.a15 \
"

do_install:append:u51() {
    install -d ${D}${libdir}
    install -m 0755 ${WORKDIR}/libjpeg.so.62.3.0.a15 ${D}${libdir}/libjpeg.so.62.3.0
}

SRC_URI:append:u52 = " \
    file://libjpeg.so.62.3.0.a15 \
"

do_install:append:u52() {
    install -d ${D}${libdir}
    install -m 0755 ${WORKDIR}/libjpeg.so.62.3.0.a15 ${D}${libdir}/libjpeg.so.62.3.0
}

SRC_URI:append:u53 = " \
    file://libjpeg.so.62.3.0.a15 \
"

do_install:append:u53() {
    install -d ${D}${libdir}
    install -m 0755 ${WORKDIR}/libjpeg.so.62.3.0.a15 ${D}${libdir}/libjpeg.so.62.3.0
}

SRC_URI:append:u54 = " \
    file://libjpeg.so.62.3.0.a15 \
"

do_install:append:u54() {
    install -d ${D}${libdir}
    install -m 0755 ${WORKDIR}/libjpeg.so.62.3.0.a15 ${D}${libdir}/libjpeg.so.62.3.0
}

SRC_URI:append:u55 = " \
    file://libjpeg.so.62.3.0.a15 \
"

do_install:append:u55() {
    install -d ${D}${libdir}
    install -m 0755 ${WORKDIR}/libjpeg.so.62.3.0.a15 ${D}${libdir}/libjpeg.so.62.3.0
}

SRC_URI:append:u56 = " \
    file://libjpeg.so.62.3.0.a15 \
"

do_install:append:u56() {
    install -d ${D}${libdir}
    install -m 0755 ${WORKDIR}/libjpeg.so.62.3.0.a15 ${D}${libdir}/libjpeg.so.62.3.0
}

SRC_URI:append:u57 = " \
    file://libjpeg.so.62.3.0.a15 \
"

do_install:append:u57() {
    install -d ${D}${libdir}
    install -m 0755 ${WORKDIR}/libjpeg.so.62.3.0.a15 ${D}${libdir}/libjpeg.so.62.3.0
}

SRC_URI:append:u532 = " \
    file://libjpeg.so.62.3.0.a15 \
"

do_install:append:u532() {
    install -d ${D}${libdir}
    install -m 0755 ${WORKDIR}/libjpeg.so.62.3.0.a15 ${D}${libdir}/libjpeg.so.62.3.0
}

SRC_URI:append:u533 = " \
    file://libjpeg.so.62.3.0.a15 \
"

do_install:append:u533() {
    install -d ${D}${libdir}
    install -m 0755 ${WORKDIR}/libjpeg.so.62.3.0.a15 ${D}${libdir}/libjpeg.so.62.3.0
}

SRC_URI:append:u571 = " \
    file://libjpeg.so.62.3.0.a15 \
"

do_install:append:u571() {
    install -d ${D}${libdir}
    install -m 0755 ${WORKDIR}/libjpeg.so.62.3.0.a15 ${D}${libdir}/libjpeg.so.62.3.0
}

INSANE_SKIP:${PN} = "already-stripped"
