FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append:arm = " file://fix-build-for-arm.patch"
