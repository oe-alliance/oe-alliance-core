SUMMARY = "Plugin for gstreamer: dvbmediasink"
SECTION = "multimedia"
LICENSE = "MIT | LGPLv2.1"
LIC_FILES_CHKSUM = "file://src/gstdvbaudiosink.c;beginline=1;endline=45;md5=023ebb8eaef9b8cce8591a9d96638392 \
                    file://src/gstdvbvideosink.c;beginline=1;endline=44;md5=b597d3f0a4e3b49db42d2b5140bd7004"
DEPENDS = "gstreamer gst-plugins-base"
RDEPENDS_${PN} = "gst-ffmpeg"

PR = "r26"

inherit autotools git-project

BRANCH="master"
SRCREV = "9cd3627a42178b74e3aec24413f4b0e0be76de59"
SRC_URI = "git://schwerkraft.elitedvb.net/dvbmediasink/dvbmediasink.git;protocol=git;branch=${BRANCH} \
		file://0001-dvbaudiosink-enable-async.patch \
		file://0002-dvbaudiosink-emulate-bcm7405-caps.patch \
		file://0003-dvbaudiosink-support-ac3plus.patch \
		file://0004-dvbaudiosink-remove_wma.patch \
		file://0005-dvbaudiosink-remove-mp4a-non-lc.patch \
"

FILES_${PN} = "${libdir}/gstreamer-0.10/*.so"
FILES_${PN}-dev += "${libdir}/gstreamer-0.10/*.la"
FILES_${PN}-staticdev += "${libdir}/gstreamer-0.10/*.a"
FILES_${PN}-dbg += "${libdir}/gstreamer-0.10/.debug"
