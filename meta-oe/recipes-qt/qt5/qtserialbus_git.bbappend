# package is machine specific
PACKAGE_ARCH := "${MACHINE_ARCH}"

SRC_URI_append = "\
           file://0001-fix-compile-error.patch \
"

FILESEXTRAPATHS_prepend := "${THISDIR}/qtserialbus-git:"
