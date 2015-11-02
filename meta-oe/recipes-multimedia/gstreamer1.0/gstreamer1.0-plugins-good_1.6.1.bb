include gstreamer1.0-plugins-good.inc
require mips-only.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=a6f89e2100d9b6cdffcea4f398e37343 \
                    file://common/coverage/coverage-report.pl;beginline=2;endline=17;md5=a4e1830fce078028c8f0974161272607 \
                    file://gst/replaygain/rganalysis.c;beginline=1;endline=23;md5=b60ebefd5b2f5a8e0cab6bfee391a5fe"

SRC_URI += "file://0001-gstrtpmp4gpay-set-dafault-value-for-MPEG4-without-co.patch \
"

SRC_URI[md5sum] = "2c20faa2406afb840699e97e913d8fb4"
SRC_URI[sha256sum] = "0cc3f90e4322efe1f774272e8fe5c185be37cf7999cd5ca7e0e0607e03d56a57"

S = "${WORKDIR}/gst-plugins-good-${PV}"