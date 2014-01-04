SUMMARY = "Plugin for gstreamer: dvbmediasink"
SECTION = "multimedia"
LICENSE = "MIT | LGPLv2.1"
LIC_FILES_CHKSUM = "file://src/gstdvbaudiosink.c;beginline=1;endline=45;md5=023ebb8eaef9b8cce8591a9d96638392 \
                    file://src/gstdvbvideosink.c;beginline=1;endline=44;md5=b597d3f0a4e3b49db42d2b5140bd7004"
DEPENDS = "gstreamer gst-plugins-base"
RDEPENDS_${PN} = "gst-ffmpeg"

inherit autotools schwerkraft-git

PR = "r20"

SRCREV = "6d79a5f2b31602925f2687bb82ba6a55f8013096"
SCHWERKRAFT_PROJECT = "dvbmediasink"

SRC_URI += " \
    file://getdecodertime.patch \
    file://0002-Set-only-by-hardware-supported-audio-mpeg-4-profile.patch \
    file://vuplus_wma_eac3_padtemplate.patch \
"

FILES_${PN} = "${libdir}/gstreamer-0.10/*.so"
FILES_${PN}-dev += "${libdir}/gstreamer-0.10/*.la"
FILES_${PN}-staticdev += "${libdir}/gstreamer-0.10/*.a"
FILES_${PN}-dbg += "${libdir}/gstreamer-0.10/.debug"
