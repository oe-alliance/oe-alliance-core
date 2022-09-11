SUMMARY = "libgles for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
PACKAGE_ARCH := "${MACHINE_ARCH}"

PR = "r5-${SRCDATE}"

PROVIDES = "virtual/libgles1 virtual/libgles2 virtual/egl"
RPROVIDES_${PN} = "virtual/libgles1 virtual/libgles2 virtual/egl libEGL.so libGLESv2.so libmali.so"

SRC_URI = "https://source.mynonpublic.com/dinobot/${SOC_FAMILY}-opengl-armhf-${SRCDATE}.zip \
 file://libm-2.27.so.zip \
"

S = "${WORKDIR}/usr"

SOLIBS = ".so"
FILES_SOLIBSDEV = ""

# Disable anything auto depending
EXCLUDE_FROM_SHLIBS = "1"

PRIVATE_LIBS_${PN} = "libm-2.27.so"

do_configure() {
}

do_compile() {
}
do_package_qa() {
}

do_install() {
    install -d ${D}${libdir}
    install -m 0755 ${S}/lib/libMali.so ${D}${libdir}
    ln -s libMali.so ${D}${libdir}/libmali.so
    ln -s libMali.so ${D}${libdir}/libGLESv2.so.2.0
    ln -s libGLESv2.so.2.0 ${D}${libdir}/libGLESv2.so.2
    ln -s libGLESv2.so.2 ${D}${libdir}/libGLESv2.so
    ln -s libMali.so ${D}${libdir}/libGLESv1_CM.so.1.1
    ln -s libGLESv1_CM.so.1.1 ${D}${libdir}/libGLESv1_CM.so.1
    ln -s libGLESv1_CM.so.1 ${D}${libdir}/libGLESv1_CM.so
    ln -s libMali.so ${D}${libdir}/libEGL.so.1.4
    ln -s libEGL.so.1.4 ${D}${libdir}/libEGL.so.1
    ln -s libEGL.so.1 ${D}${libdir}/libEGL.so
    install -d ${D}/${includedir}
    install -m 0644 ${S}/include/hi_dbe.h ${D}${includedir}/
    for d in EGL GLES GLES2 KHR; do
        install -d ${D}${includedir}/$d
        for f in ${S}/include/$d/*.h; do
           install -m 0644 $f ${D}${includedir}/$d/
        done
    done
    install -d ${D}/lib
    install -m 0755 ${WORKDIR}/libm-2.27.so ${D}/lib
}

FILES_${PN} = "/usr/lib/* /lib"
FILES_${PN}-dev = "/usr/include/*"

INSANE_SKIP_${PN} += "already-stripped dev-so"
