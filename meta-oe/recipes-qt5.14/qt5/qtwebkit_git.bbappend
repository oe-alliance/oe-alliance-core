# package is machine specific
PACKAGE_ARCH := "${MACHINE_ARCH}"

DEPENDS += " libwebp"

SRC_URI += " \
    file://0001-Qtwebkit-platform-setting.patch \
    file://0002-Qtwebkit-without-x11.patch \
"

PACKAGECONFIG = " "

FILESEXTRAPATHS_prepend := "${THISDIR}/qtwebkit-git:"

SRCREV = "ab1bd15209abaf7effc51dbc2f272c5681af7223"
#SRCREV = "beaeeb99881184fd368c121fcbb1a31c78b794a3"


INSANE_SKIP_${PN} += "file-rdeps"
