SUMMARY = "AArch64 Mali-G31 EGL and OpenGL ES for native framebuffer applications"
require conf/license/license-close.inc
COMPATIBLE_MACHINE = "^gtxcombo$"
PACKAGE_ARCH = "${MACHINE_ARCH}"
DEPENDS = "libdrm gtx-mali"
PROVIDES += "virtual/egl virtual/libgles2"
RDEPENDS:${PN} += "kernel-module-mali-kbase"
RPROVIDES:${PN} += "libegl libegl1 libgles2 libglesv2-2 virtual-egl virtual-libgles2"

GPU_REV = "8bfb8ebe38f615907852ada7ff375a04f53f3e81"
SRC_URI = "${AMLOGIC_SOURCE_MIRROR}/opengl-meson-${GPU_REV}.tar.gz"
SRC_URI[sha256sum] = "9468dad254ef7b2486f738f594c5a78fbb1926a6661923ae10054d9960978ec7"
S = "${UNPACKDIR}/opengl-meson-${GPU_REV}"
do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
    install -d ${D}${includedir} ${D}${libdir}/pkgconfig
    for api in EGL GLES GLES2 GLES3 KHR; do
        cp -R ${S}/include/$api ${D}${includedir}/
    done
    install -m 0644 ${S}/include/EGL_platform/platform_fbdev/*.h ${D}${includedir}/EGL/
    install -m 0644 ${S}/lib/pkgconfig/egl.pc ${S}/lib/pkgconfig/glesv2.pc ${D}${libdir}/pkgconfig/
    install -m 0755 ${S}/lib/arm64/dvalin/r37p0/fbdev/libMali.so ${D}${libdir}/
    for lib in libEGL.so.1 libGLESv1_CM.so.1 libGLESv2.so.2; do
        ln -s libMali.so ${D}${libdir}/$lib
        ln -s $lib ${D}${libdir}/${lib%.*}
    done
}

# libMali.so is the vendor ELF's actual SONAME.
FILES:${PN} += "${libdir}/libMali.so"
FILES:${PN}-dev = "${includedir} ${libdir}/pkgconfig ${libdir}/libEGL.so ${libdir}/libGLESv1_CM.so ${libdir}/libGLESv2.so"
INSANE_SKIP:${PN} += "already-stripped dev-so ldflags"
