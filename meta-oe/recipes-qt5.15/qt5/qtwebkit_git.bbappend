FILESEXTRAPATHS_prepend := "${THISDIR}/qtwebkit-git:"
# package is machine specific
PACKAGE_ARCH := "${MACHINE_ARCH}"

inherit ${PYTHON_PN}-dir ${PYTHON_PN}native

DEPENDS += " libwebp qtdeclarative qtsensors qtlocation"

SRC_URI += " \
    file://0001-Qtwebkit-platform-setting.patch \
    file://0002-Qtwebkit-without-x11.patch \
    ${@bb.utils.contains('MACHINE_FEATURES', 'no-opengl', 'file://0003-Qtwebkit-without-opengl.patch', '', d)} \
"

SRCREV = "ac8ebc6c3a56064f88f5506e5e3783ab7bee2456"

PACKAGECONFIG = " "

CXXFLAGS_append_osmini4k = " -Wno-deprecated-copy -DBROADCOM_PLATFORM"

INSANE_SKIP_${PN} += "file-rdeps"
