# package is machine specific
PACKAGE_ARCH := "${MACHINE_ARCH}"

DEPENDS += " libwebp"

SRC_URI += " \
    file://0001-Qtwebkit-platform-setting.patch \
    file://0002-Qtwebkit-without-x11.patch \
"

SRCREV = "ac8ebc6c3a56064f88f5506e5e3783ab7bee2456"

PACKAGECONFIG = " "

FILESEXTRAPATHS_prepend := "${THISDIR}/qtwebkit-git:"

CXXFLAGS_append_osmini4k = " -Wno-deprecated-copy -DBROADCOM_PLATFORM"

INSANE_SKIP_${PN} += "file-rdeps"

inherit ${PYTHON_PN}-dir ${PYTHON_PN}native
