DEFAULT_PREFERENCE = "-1"
include gstreamer1.0-plugins-base.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=c54ce9345727175ff66d17b67ff51f58 \
                    file://common/coverage/coverage-report.pl;beginline=2;endline=17;md5=a4e1830fce078028c8f0974161272607 \
                    file://COPYING.LIB;md5=6762ed442b3822387a51c92d928ead0d \
                   "
SRC_URI = " \
    git://anongit.freedesktop.org/gstreamer/gst-plugins-base;branch=master;name=master \
    git://anongit.freedesktop.org/gstreamer/common;destsuffix=git/common;name=common \
    file://make-gio_unix_2_0-dependency-configurable.patch \
"
SRC_URI += " \
	file://get-caps-from-src-pad-when-query-caps.patch \
	file://taglist-not-send-to-down-stream-if-all-the-frame-cor.patch \
	file://0001-riff-media-added-fourcc-to-all-mpeg4-video-caps.patch \
	file://0001-riff-media-added-fourcc-to-all-ffmpeg-mpeg4-video-cav2.patch \
	file://subparse-avoid-false-negatives-dealing-with-UTF-8.patch \
"

S = "${WORKDIR}/git"
SRCREV_master = "de60d195c057f40f33253f488e687d8158d98118"
SRCREV_common = "1b39f6d85a3d51ac6d1b44d8c821fd9b76b34454"
SRCREV_FORMAT = "master"
inherit gitpkgv

do_configure_prepend() {
	cd ${S}
	./autogen.sh --noconfigure
	cd ${B}
}

