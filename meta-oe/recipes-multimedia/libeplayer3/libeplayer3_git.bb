DESCRIPTION = "Library to play files in enigma2 using ffmpeg"
HOMEPAGE = "https://github/Taapat/libeplayer3"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING.GPLv2;md5=b234ee4d69f5fce4486a80fdaf4a4263"

DEPENDS = "ffmpeg"

def add_gst_provides():
    plugin_list = ["bad", "bad-accurip", "bad-adpcmdec", "bad-adpcmenc", "bad-aiff", "bad-asfmux", "bad-audiofxbad", "bad-audiomixer", "bad-audiovisualizers", "bad-autoconvert", "bad-bayer", "bad-bluez", "bad-bz2", "bad-camerabin2", "bad-coloreffects", "bad-compositor", "bad-curl", "bad-dashdemux", "bad-dataurisrc", "bad-debugutilsbad", "bad-decklink", "bad-dtsdec", "bad-dvb", "bad-dvbsuboverlay", "bad-dvdspu", "bad-faac", "bad-faad", "bad-fbdevsink", "bad-festival", "bad-fieldanalysis", "bad-freeverb", "bad-frei0r", "bad-gaudieffects", "bad-gdp", "bad-geometrictransform", "bad-hls", "bad-id3tag", "bad-inter", "bad-interlace", "bad-ivfparse", "bad-ivtc", "bad-y4mdec", "bad-yadif", "bad-jp2kdecimator", "bad-jpegformat", "bad-meta", "bad-midi", "bad-mms", "bad-mpegpsdemux", "bad-mpegpsmux", "bad-mpegtsdemux", "bad-mpegtsmux", "bad-mxf", "bad-neonhttpsrc", "bad-pcapparse", "bad-pnm", "bad-rawparse", "bad-removesilence", "bad-rfbsrc", "bad-rtmp", "bad-rtpbad", "bad-rtponvif", "bad-sbc", "bad-sdpelem", "bad-segmentclip", "bad-shm", "bad-siren", "bad-smooth", "bad-smoothstreaming", "bad-sndfile", "bad-speed", "bad-stereo", "bad-subenc", "bad-vcdsrc", "bad-videofiltersbad", "bad-videoframe-audiolevel", "bad-videoparsersbad", "bad-videosignal", "bad-vmnc", "bad-webp", "bad-fragmented", "base-adder", "base-alsa", "base-app", "base-apps", "base-audioconvert", "base-audiorate", "base-audioresample", "base-audiotestsrc", "base-cdparanoia", "base-encodebin", "base-gio", "base-ivorbisdec", "base-meta", "base-ogg", "base-playback", "base-subparse", "base-tcp", "base-theora", "base-typefindfunctions", "base-videoconvert", "base-videorate", "base-videoscale", "base-videotestsrc", "base-volume", "base-vorbis", "good", "good-alaw", "good-alpha", "good-alphacolor", "good-apetag", "good-audiofx", "good-audioparsers", "good-auparse", "good-autodetect", "good-avi", "good-cutter", "good-debug", "good-deinterlace", "good-dtmf", "good-effectv", "good-equalizer", "good-flac", "good-flv", "good-flxdec", "good-goom", "good-goom2k1", "good-icydemux", "good-id3demux", "good-imagefreeze", "good-interleave", "good-isomp4", "good-y4menc", "good-jpeg", "good-level", "good-matroska", "good-meta", "good-mulaw", "good-multifile", "good-multipart", "good-navigationtest", "good-ossaudio", "good-png", "good-replaygain", "good-rtp", "good-rtpmanager", "good-rtsp", "good-shapewipe", "good-smpte", "good-souphttpsrc", "good-spectrum", "good-speex", "good-taglib", "good-udp", "good-videobox", "good-videocrop", "good-videofilter", "good-videomixer", "good-wavenc", "good-wavparse", "ugly", "ugly-a52dec", "ugly-amrnb", "ugly-amrwbdec", "ugly-asf", "ugly-cdio", "ugly-dvdlpcmdec", "ugly-dvdread", "ugly-dvdsub", "ugly-lame", "ugly-mad", "ugly-meta", "ugly-mpeg2dec", "ugly-rmdemux", "ugly-xingmux"]
    return "gstreamer1.0-libav gst-plugin-subsink gstreamer1.0-plugin-subsink" + " gst-plugins-".join(plugin_list) + " gstreamer1.0-plugins-".join(plugin_list)

GST_PROVIDES = "${@add_gst_provides()}"

RPROVIDES_${PN} = " ${@bb.utils.contains("MACHINE_FEATURES", "nogstreamer", "${GST_PROVIDES}", "", d)} "

inherit gitpkgv autotools

SRCREV = "${AUTOREV}"
PV = "3.1+git${SRCPV}"
PKGV = "3.1+git${GITPKGV}"
PKG_${PN} = "${PN}"

SRC_URI = "git://github.com/Taapat/libeplayer3.git"

S = "${WORKDIR}/git"

do_install_append () {
	install -d ${D}${includedir}/libeplayer3
	install -m 644 ${S}/include/*.h ${D}${includedir}/libeplayer3
}

FILES_${PN}-dev += "${includedir}/libeplayer3"
