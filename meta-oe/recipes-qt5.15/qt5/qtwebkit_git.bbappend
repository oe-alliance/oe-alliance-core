FILESEXTRAPATHS:prepend := "${THISDIR}/qtwebkit-git:"
# package is machine specific
PACKAGE_ARCH := "${MACHINE_ARCH}"

DEPENDS += " libwebp qtsensors qtlocation patchelf-native"
RDEPENDS:${PN} += " qtdeclarative qtsensors qtlocation"

SRC_URI += " \
    file://0001-Qtwebkit-platform-setting.patch \
    file://0002-Qtwebkit-without-x11.patch \
    ${@bb.utils.contains('MACHINE_FEATURES', 'no-opengl', 'file://0003-Qtwebkit-without-opengl.patch', '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'hisil-${HICHIPSET}', 'file://0003-workaround-segment-error.patch', '', d)} \
"

PACKAGECONFIG = " "

CXXFLAGS:append = " -Wno-deprecated-copy -DBROADCOM_PLATFORM"

INSANE_SKIP:${PN} += "file-rdeps"
