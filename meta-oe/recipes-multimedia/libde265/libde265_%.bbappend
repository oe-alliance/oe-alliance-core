FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append:mipsarch = " file://0001-fix-build-with-old-sdl.patch"
