include gstreamer1.0-plugins-ugly.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=a6f89e2100d9b6cdffcea4f398e37343 \
                    file://tests/check/elements/xingmux.c;beginline=1;endline=21;md5=4c771b8af188724855cb99cadd390068 "

SRC_URI[md5sum] = "abd832c5cab1a37fb1d9d15fb08e6e59"
SRC_URI[sha256sum] = "afe2300130aaba910b8d5fab8d1fdf8b001ff4893ec1ac57b5d8766836cd81e9"
S = "${WORKDIR}/gst-plugins-ugly-${PV}"

