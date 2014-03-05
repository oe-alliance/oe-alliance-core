SUMMARY = "Plugin for gstreamer: dvbmediasink"
SECTION = "multimedia"
LICENSE = "MIT | LGPLv2.1"
LIC_FILES_CHKSUM = "file://src/gstdvbaudiosink.c;beginline=1;endline=45;md5=023ebb8eaef9b8cce8591a9d96638392 \
                    file://src/gstdvbvideosink.c;beginline=1;endline=44;md5=b597d3f0a4e3b49db42d2b5140bd7004"
DEPENDS = "gstreamer gst-plugins-base"
RDEPENDS_${PN} = "gst-ffmpeg"
BRANCH="master"
PR = "r20"

inherit autotools git-project
SRCREV = "91738211ef106ae7d14c1ccc5c4dd26c0f2dbf58"
SRC_URI = "git://schwerkraft.elitedvb.net/dvbmediasink/dvbmediasink.git;protocol=git;branch=${BRANCH} \
		file://0001-dvbaudiosink-enable-async.patch;apply=yes \
		file://0002-dvbaudiosink-emulate-bcm7405-caps.patch;apply=yes \
		file://0003-dvbaudiosink-support-ac3plus.patch;apply=yes \
		file://0004-dvbaudiosink-remove_wma.patch;apply=yes \
		file://0005-dvbaudiosink-remove-mp4a-non-lc.patch;apply=yes \
"

FILES_${PN} = "${libdir}/gstreamer-0.10/*.so"
FILES_${PN}-dev += "${libdir}/gstreamer-0.10/*.la"
FILES_${PN}-staticdev += "${libdir}/gstreamer-0.10/*.a"
FILES_${PN}-dbg += "${libdir}/gstreamer-0.10/.debug"
