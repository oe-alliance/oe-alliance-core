FILESEXTRAPATHS:prepend := "${THISDIR}/harfbuzz:"

SRC_URI:append:sh4 = " file://support-old-gcc.patch"

CFLAGS:append:sh4 = " -std=gnu11"
