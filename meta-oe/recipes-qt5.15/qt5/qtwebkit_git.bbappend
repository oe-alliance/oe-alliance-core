FILESEXTRAPATHS:prepend := "${THISDIR}/qtwebkit-git:"
# package is machine specific
PACKAGE_ARCH := "${MACHINE_ARCH}"

DEPENDS += " libwebp qtsensors qtlocation patchelf-native"
RDEPENDS:${PN} += " qtdeclarative qtsensors qtlocation"

SRC_URI += " \
    file://0001-Qtwebkit-platform-setting.patch \
    file://0002-Qtwebkit-without-x11.patch \
    ${@bb.utils.contains('MACHINE_FEATURES', 'noopengl', 'file://0003-Qtwebkit-without-opengl.patch', '', d)} \
"

SRCREV = "ac8ebc6c3a56064f88f5506e5e3783ab7bee2456"

CXXFLAGS:append = " -Wno-deprecated-copy -DBROADCOM_PLATFORM"
