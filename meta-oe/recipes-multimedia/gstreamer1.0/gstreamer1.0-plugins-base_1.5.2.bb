include gstreamer1.0-plugins-base.inc
require mips-only.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=c54ce9345727175ff66d17b67ff51f58 \
                    file://common/coverage/coverage-report.pl;beginline=2;endline=17;md5=a4e1830fce078028c8f0974161272607 \
                    file://COPYING.LIB;md5=6762ed442b3822387a51c92d928ead0d \
                   "

SRC_URI += " \
	file://get-caps-from-src-pad-when-query-caps.patch \
	file://taglist-not-send-to-down-stream-if-all-the-frame-cor.patch \
	file://0001-riff-media-added-fourcc-to-all-mpeg4-video-caps.patch \
	file://subparse-avoid-false-negatives-dealing-with-UTF-8.patch \
"

SRC_URI[md5sum] = "6d179b69e528786aa3b5f682437567f1"
SRC_URI[sha256sum] = "a7d65acfb6760eb7f23e1b635a13f643406541f9350a01a8bd84246219db0071"

S = "${WORKDIR}/gst-plugins-base-${PV}"

