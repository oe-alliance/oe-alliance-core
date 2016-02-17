include gstreamer1.0-plugins-good.inc
require mips-only.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=a6f89e2100d9b6cdffcea4f398e37343 \
                    file://common/coverage/coverage-report.pl;beginline=2;endline=17;md5=a4e1830fce078028c8f0974161272607 \
                    file://gst/replaygain/rganalysis.c;beginline=1;endline=23;md5=b60ebefd5b2f5a8e0cab6bfee391a5fe"

SRC_URI += "file://0001-gstrtpmp4gpay-set-dafault-value-for-MPEG4-without-co.patch \
"

SRC_URI[md5sum] = "7d4afd01a460aec53404bdda6b535c5b"
SRC_URI[sha256sum] = "f0789d0e4e4e60046327fcf8908c429b6c6c0576a88823497ddc1ea8bfddc993"

S = "${WORKDIR}/gst-plugins-good-${PV}"
