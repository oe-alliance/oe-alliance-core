SUMMARY = "libgles for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
PACKAGE_ARCH = "${MACHINE_ARCH}"
require conf/license/license-close.inc

PV = "15.1"
PR = "${SRCDATE}.${SRCDATE_PR}"

PROVIDES = "virtual/libgles1 virtual/libgles2 virtual/egl libgles"
RPROVIDES:${PN} = "virtual-libgles1 virtual-libgles2 virtual-egl libEGL.so libGLESv2.so libgles"

DEPENDS = "patchelf-native"

SRC_URI = "https://source.mynonpublic.com/vuplus/release/libgles/libgles-${MACHINE}-${PV}-${PR}.tar.gz"

S = "${UNPACKDIR}/libgles-${MACHINE}"

INSANE_SKIP:${PN} += "already-stripped dev-so ldflags"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
PRIVATE_LIBS:${PN} = "libEGL.so \
libv3ddriver.so \
libGLESv2.so"

do_install() {
    install -d ${D}${libdir}
    install -m 0755 ${S}/lib/*.so ${D}${libdir}
    patchelf --set-soname libv3ddriver.so ${D}${libdir}/libv3ddriver.so
    ln -s libv3ddriver.so ${D}${libdir}/libEGL.so
    ln -s libv3ddriver.so ${D}${libdir}/libGLESv2.so
    install -d ${D}${includedir}
    cp -a ${S}/include/* ${D}${includedir}/
    install -d ${D}${libdir}/pkgconfig
    cat > ${D}${libdir}/pkgconfig/egl.pc <<EOF
prefix=${prefix}
exec_prefix=${exec_prefix}
libdir=${libdir}
includedir=${includedir}

Name: EGL
Description: Broadcom/Vu+ EGL
Version: ${PV}
Libs: -L${libdir} -lEGL
Cflags: -I${includedir}
EOF
    cat > ${D}${libdir}/pkgconfig/glesv2.pc <<EOF
prefix=${prefix}
exec_prefix=${exec_prefix}
libdir=${libdir}
includedir=${includedir}

Name: GLESv2
Description: Broadcom/Vu+ OpenGL ES 2
Version: ${PV}
Libs: -L${libdir} -lGLESv2
Cflags: -I${includedir}
EOF
    cat > ${D}${libdir}/pkgconfig/libgles.pc <<EOF
prefix=${prefix}
exec_prefix=${exec_prefix}
libdir=${libdir}
includedir=${includedir}

Name: libgles
Description: Broadcom/Vu+ EGL and OpenGL ES
Version: ${PV}
Libs: -L${libdir} -lEGL -lGLESv2
Cflags: -I${includedir}
EOF
    cat > ${D}${libdir}/pkgconfig/nxpl.pc <<EOF
prefix=${prefix}
exec_prefix=${exec_prefix}
libdir=${libdir}
includedir=${includedir}

Name: NXPL
Description: Broadcom/Vu+ Nexus platform layer
Version: ${PV}
Libs: -L${libdir} -lnxpl -ldvb_client -ldvb_base
Cflags: -I${includedir}
EOF
}

do_package_qa() {
}

FILES:${PN} = "${libdir}/*.so"
FILES:${PN}-dev = "${includedir}/* ${libdir}/pkgconfig/*"
