include gstreamer1.0-plugins-ugly.inc
require mips-only.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=a6f89e2100d9b6cdffcea4f398e37343 \
                    file://tests/check/elements/xingmux.c;beginline=1;endline=21;md5=4c771b8af188724855cb99cadd390068 "

SRC_URI[md5sum] = "66bbae1f7d34624255787f887d0a1dbb"
SRC_URI[sha256sum] = "4b1e1e2d260af701b59a1bc393ca497e7954228a6500a8c113fd1c7ca5e8709e"

S = "${WORKDIR}/gst-plugins-ugly-${PV}"

