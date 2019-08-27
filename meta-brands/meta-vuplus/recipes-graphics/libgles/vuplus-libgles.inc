SUMMARY = "libgles for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"
require conf/license/license-close.inc

PV="15.1"
PR="${SRCDATE}.${SRCDATE_PR}"

PROVIDES = "virtual/libgles2 virtual/egl"
PROVIDES += "libgles"
RPROVIDES_${PN} += "libgles"

SRC_URI = "http://archive.vuplus.com/download/build_support/vuplus/libgles-${MACHINE}-${PV}-${PR}.tar.gz"

S = "${WORKDIR}/libgles-${MACHINE}"

INSANE_SKIP_${PN} += "already-stripped"
INHIBIT_PACKAGE_STRIP = "1"
PRIVATE_LIBS_${PN} = "libEGL.so \
libv3ddriver.so \
libGLESv2.so"

do_install() {
    install -d ${D}${libdir}/
    install -m 0755 ${S}/lib/*.so ${D}${libdir}/
    ln -s libv3ddriver.so ${D}${libdir}/libEGL.so
    ln -s libv3ddriver.so ${D}${libdir}/libGLESv2.so
    install -d ${D}/usr/include
    cp -a ${S}/include/* ${D}/usr/include/
}

do_package_qa() {
}

FILES_${PN} = "${libdir}/*"
FILES_${PN}-dev = "/usr/include/*"

