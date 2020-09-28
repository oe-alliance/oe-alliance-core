# package is machine specific
PACKAGE_ARCH := "${MACHINE_ARCH}"

DEPENDS += " libwebp"

SRC_URI += " \
    file://0001-Qtwebkit-platform-setting.patch \
    file://0002-Qtwebkit-without-x11.patch \
    file://0003-fix-build-with-bison-3.7.patch \
"

PACKAGECONFIG = " "

FILESEXTRAPATHS_prepend := "${THISDIR}/qtwebkit-git:"

SRCREV = "ab1bd15209abaf7effc51dbc2f272c5681af7223"
#SRCREV = "beaeeb99881184fd368c121fcbb1a31c78b794a3"

CXXFLAGS_append_osmini4k = " -Wno-deprecated-copy -DBROADCOM_PLATFORM"

INSANE_SKIP_${PN} += "file-rdeps"

inherit ${PYTHON_PN}-dir ${PYTHON_PN}native
