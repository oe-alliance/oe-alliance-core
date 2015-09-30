include gstreamer1.0-plugins-ugly.inc
require mips-only.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=a6f89e2100d9b6cdffcea4f398e37343 \
                    file://tests/check/elements/xingmux.c;beginline=1;endline=21;md5=4c771b8af188724855cb99cadd390068 "

SRC_URI[md5sum] = "7df6ab044d7754c1087375ce5f770be8"
SRC_URI[sha256sum] = "91178dc0d687a83c083190a905681d3a66901374b1004fc52cd300b7802e5f06"

S = "${WORKDIR}/gst-plugins-ugly-${PV}"