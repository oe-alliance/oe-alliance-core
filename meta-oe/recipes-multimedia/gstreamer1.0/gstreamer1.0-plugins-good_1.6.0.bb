include gstreamer1.0-plugins-good.inc
require mips-only.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=a6f89e2100d9b6cdffcea4f398e37343 \
                    file://common/coverage/coverage-report.pl;beginline=2;endline=17;md5=a4e1830fce078028c8f0974161272607 \
                    file://gst/replaygain/rganalysis.c;beginline=1;endline=23;md5=b60ebefd5b2f5a8e0cab6bfee391a5fe"

SRC_URI += "file://0001-gstrtpmp4gpay-set-dafault-value-for-MPEG4-without-co.patch \
"

SRC_URI[md5sum] = "a28d0d46c929f0064432d2fd94c12ef1"
SRC_URI[sha256sum] = "a0915639595305e48884656e22b16fda7c6892aa02cdb3eb43e23dab6e6b81fa"

S = "${WORKDIR}/gst-plugins-good-${PV}"