FILESEXTRAPATHS:prepend := "${THISDIR}/qtwebkit-git:"
# package is machine specific
PACKAGE_ARCH := "${MACHINE_ARCH}"

DEPENDS += " libwebp qtsensors qtlocation patchelf-native"
RDEPENDS:${PN} += " qtdeclarative qtsensors qtlocation"

SRC_URI += " \
    file://0001-Qtwebkit-platform-setting.patch \
    file://0002-Qtwebkit-without-x11.patch \
    ${@bb.utils.contains('MACHINE_FEATURES', 'no-opengl', 'file://0003-Qtwebkit-without-opengl.patch', '', d)} \
"

# HACK Close libEGL.so issue fix rpatch
do_install_append() {
    if [ -e ${PKG_CONFIG_SYSROOT_DIR}${libdir}/libEGL.so ]; then
        patchelf --remove-needed ${PKG_CONFIG_SYSROOT_DIR}${libdir}/libEGL.so ${D}${libdir}/libQt5WebKit.so.5.212.0
        patchelf --add-needed libEGL.so ${D}${libdir}/libQt5WebKit.so.5.212.0
    fi
}

PACKAGECONFIG = " "

CXXFLAGS:append = " -Wno-deprecated-copy -DBROADCOM_PLATFORM"

INSANE_SKIP:${PN} += "file-rdeps"
