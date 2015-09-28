include gstreamer1.0-plugins-ugly.inc
require mips-only.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=a6f89e2100d9b6cdffcea4f398e37343 \
                    file://tests/check/elements/xingmux.c;beginline=1;endline=21;md5=4c771b8af188724855cb99cadd390068 "

SRC_URI[md5sum] = "65ae67b73e8b9f5431d9520d631f441a"
SRC_URI[sha256sum] = "e69638e34adcad3d5b75e3dcd8eb4be73e140ebe68067b3ecdafd520dbe4df40"

S = "${WORKDIR}/gst-plugins-ugly-${PV}"

