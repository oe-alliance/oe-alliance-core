FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://0003-Allow-enable-DRI-without-DRI-drivers.patch"

PACKAGECONFIG[dri-no-drivers] = "-Ddri=true -Ddri-drivers=${@strip_comma('${DRIDRIVERS}')}, -Ddri=false -Ddri-drivers='', xorgproto libdrm"

do_install:append() {
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

PROVIDES:remove = "virtual/libgles1 virtual/libgles2 virtual/egl virtual/libgbm"
PROVIDES += "virtual/egl-native virtual/nativesdk-egl"

REQUIRED_DISTRO_FEATURES = ""
ANY_OF_DISTRO_FEATURES:class-target = ""

PACKAGECONFIG:class-target = "opengl egl gles gbm dri-no-drivers"

RREPLACES:${PN} = "airdigital-mali-utgard-headers airdigital-v3ddriver-headers hd-v3ddriver-headers gfutures-mali-utgard-headers ceryon-v3ddriver-headers xtrend-v3ddriver-headers skylake-v3ddriver-headers formuler-v3ddriver-headers ax-v3ddriver-headers"
RCONFLICTS:${PN} = "airdigital-mali-utgard-headers airdigital-v3ddriver-headers hd-v3ddriver-headers gfutures-mali-utgard-headers ceryon-v3ddriver-headers xtrend-v3ddriver-headers skylake-v3ddriver-headers formuler-v3ddriver-headers ax-v3ddriver-headers"

DRIDRIVERS ??= ""
DRIDRIVERS:append:x86:class-target = ",r100,r200,nouveau,i965"
DRIDRIVERS:append:x86-64:class-target = ",r100,r200,nouveau,i965"
