FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append:sh4 = " file://0001-gnulib-include-errno-before-random.patch"
