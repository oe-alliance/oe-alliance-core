do_install_append() {
    # Remove Mesa libraries (EGL, GLESv1, GLESv2, GBM)
    # provided by SOC
    rm -f ${D}${libdir}/libGLESv1_CM.*
    rm -f ${D}${libdir}/libEGL.so.*
    rm -f ${D}${libdir}/libEGL.so
    rm -f ${D}${libdir}/libgbm.so.*
    rm -f ${D}${libdir}/libgbm.so
    rm -f ${D}${libdir}/libGLESv1*.so.*
    rm -f ${D}${libdir}/libGLESv2.so.*
    rm -f ${D}${libdir}/libGLESv2.so
}

PROVIDES_remove = "virtual/libgles1 virtual/libgles2 virtual/egl virtual/libgbm"

REQUIRED_DISTRO_FEATURES = ""
ANY_OF_DISTRO_FEATURES_class-target = ""

PACKAGECONFIG_class-target = "opengl egl gles gbm dri"

RREPLACES_${PN} = "airdigital-mali-utgard-headers airdigital-v3ddriver-headers hd-v3ddriver-headers gfutures-mali-utgard-headers ceryon-v3ddriver-headers xtrend-v3ddriver-headers skylake-v3ddriver-headers formuler-v3ddriver-headers ax-v3ddriver-headers"
RCONFLICTS_${PN} = "airdigital-mali-utgard-headers airdigital-v3ddriver-headers hd-v3ddriver-headers gfutures-mali-utgard-headers ceryon-v3ddriver-headers xtrend-v3ddriver-headers skylake-v3ddriver-headers formuler-v3ddriver-headers ax-v3ddriver-headers"
