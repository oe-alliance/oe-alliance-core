include gstreamer1.0-plugins-base.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=c54ce9345727175ff66d17b67ff51f58 \
                    file://common/coverage/coverage-report.pl;beginline=2;endline=17;md5=a4e1830fce078028c8f0974161272607 \
                    file://COPYING.LIB;md5=6762ed442b3822387a51c92d928ead0d \
                   "

SRC_URI += "file://do-not-change-eos-event-to-gap-event-if.patch \
            file://get-caps-from-src-pad-when-query-caps.patch \
"

SRC_URI[md5sum] = "0c42eca8f9e4efd56d2ce8e9249ce4a1"
SRC_URI[sha256sum] = "49cd9e8f23c416b1607b43837a09833fa03e0106929d81ead2ddfde6c0ade44b"
S = "${WORKDIR}/gst-plugins-base-${PV}"
