SUMMARY = "libgles for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
PACKAGE_ARCH := "${MACHINE_ARCH}"
require conf/license/license-close.inc

PV="15.1"
PR="${SRCDATE}.${SRCDATE_PR}"

#SRC_URI = "http://archive.vuplus.com/download/build_support/vuplus/libgles-${MACHINE}-${PV}-${PR}.tar.gz"
SRC_URI = "http://archive.vuplus.com/download/build_support/vuplus/libgles-vuduo2-${PV}-${PR}.tar.gz"

#S = "${WORKDIR}/libgles-${MACHINE}"
S = "${WORKDIR}/libgles-vuduo2"

INHIBIT_PACKAGE_STRIP = "1"
PRIVATE_LIBS_${PN} = "libEGL.so \
libv3ddriver.so \
libGLESv2.so"

do_install() {
    install -d ${D}/usr/lib/
    install -m 0755 ${S}/lib/*.so ${D}/usr/lib/
    ln -s libv3ddriver.so ${D}/usr/lib/libEGL.so
    ln -s libv3ddriver.so ${D}/usr/lib/libGLESv2.so
    install -d ${D}/usr/include
    cp -a ${S}/include/* ${D}/usr/include/
}

do_package_qa() {
}

FILES_${PN} = "/usr/lib/*"
FILES_${PN}-dev = "/usr/include/*"

