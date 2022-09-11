DESCRIPTION = "Mali OpenGL"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"
MALIVER ?= "${@bb.utils.contains('TARGET_ARCH', 'aarch64', '64' , '', d)}"

PROVIDES += " virtual/libgles2 virtual/libgles1 virtual/egl"
RPROVIDES:${PN} = "virtual/libgles2 virtual/libgles1 virtual/egl libMali.so libmali.so libEGL.so.1.4 libEGL.so.1 libEGL.so libGLESv1_CM.so.1.1 libGLESv1_CM.so.1 libGLESv1_CM.so libGLESv2.so.2.0 libGLESv2.so.2 libGLESv2.so libgbm.so"
DEPENDS = "libdrm mesa patchelf-native"

SRC_URI = "https://source.mynonpublic.com/gfutures/gfutures-mali${MALIVER}-3798mv200-${SRCDATE}.zip"

S = "${WORKDIR}"

do_configure() {
}

do_compile() {
}

do_install:append() {
    install -d ${D}${libdir}
    install -d ${D}${sysconfdir}/udev/rules.d
    echo 'KERNEL=="mali0", MODE="0660", GROUP="video"' > ${S}/50-mali.rules
    install -m 0644 ${S}/50-mali.rules ${D}${sysconfdir}/udev/rules.d/50-mali.rules
    install -m 0755 ${S}/libMali.so ${D}${libdir}
    patchelf --set-soname libMali.so ${D}${libdir}/libMali.so
    (cd ${D}/${libdir} && ln -sf libMali.so libmali.so)
    (cd ${D}/${libdir} && ln -sf libMali.so libEGL.so.1.4 \
    && ln -sf libEGL.so.1.4 libEGL.so.1 \
    && ln -sf libEGL.so.1 libEGL.so)
    (cd ${D}/${libdir} && ln -sf libMali.so libGLESv1_CM.so.1.1 \
    && ln -sf libGLESv1_CM.so.1.1 libGLESv1_CM.so.1 \
    && ln -sf libGLESv1_CM.so.1 libGLESv1_CM.so)
    (cd ${D}/${libdir} && ln -sf libMali.so libGLESv2.so.2.0 \
    && ln -sf libGLESv2.so.2.0 libGLESv2.so.2 \
    && ln -sf libGLESv2.so.2 libGLESv2.so)
}

FILES:${PN} = "${libdir} /etc/udev/rules.d/50-mali.rules"
FILES:${PN}-dev = "${incdir}"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INSANE_SKIP:${PN} += "already-stripped dev-so ldflags"

# The driver is missing EGL/GLES headers and pkgconfig files. Handle
# the conflicts as mesa and the driver are both providing the same shared libraries.
RREPLACES:${PN} = "libegl libegl1 libgles1 libglesv1-cm1 libgles2 libglesv2-2"
RPROVIDES:${PN} += "libegl libegl1 libgles1 libglesv1-cm1 libgles2 libglesv2-2"
RCONFLICTS:${PN} = "libegl libegl1 libgles1 libglesv1-cm1 libgles2 libglesv2-2"
