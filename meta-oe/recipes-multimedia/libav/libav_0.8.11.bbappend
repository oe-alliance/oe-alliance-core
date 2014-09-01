RSUGGESTS_${PN} = ""

PROVIDES =+ " libavcodec53 libavformat53"
PACKAGES =+ " libavcodec53 libavformat53"

FILESEXTRAPATHS_append := "${THISDIR}/files"

SRC_URI_append_sh4 = " \
    file://libav-fix-sh4-compile-gcc48.patch;patch=1 \
    "
