FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

CFLAGS += "-Wno-error=incompatible-pointer-types"

SRC_URI:append:mipsel = " file://add-missing-keys.patch"
