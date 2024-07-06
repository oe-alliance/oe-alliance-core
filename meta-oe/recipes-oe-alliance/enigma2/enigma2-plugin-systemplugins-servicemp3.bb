DESCRIPTION = "servicemp3 for enigma2"
AUTHOR = "OpenPLi team <info@openpli.org>"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

PACKAGE_ARCH = "${MACHINE_ARCH}"

PROVIDES += "virtual/enigma2-mediaservice"
RPROVIDES:${PN} += "virtual-enigma2-mediaservice"

GST_BASE_RDEPS = "\
	gstreamer${GST_VERSION}-plugins-base-alsa \
	gstreamer${GST_VERSION}-plugins-base-app \
	gstreamer${GST_VERSION}-plugins-base-audioconvert \
	gstreamer${GST_VERSION}-plugins-base-audioresample \
	gstreamer${GST_VERSION}-plugins-base-audiorate \
	gstreamer${GST_VERSION}-plugins-base-videoconvertscale \
	gstreamer${GST_VERSION}-plugins-base-ivorbisdec \
	gstreamer${GST_VERSION}-plugins-base-ogg \
	gstreamer${GST_VERSION}-plugins-base-opus \
	gstreamer${GST_VERSION}-plugins-base-playback \
	gstreamer${GST_VERSION}-plugins-base-subparse \
	gstreamer${GST_VERSION}-plugins-base-typefindfunctions \
	gstreamer${GST_VERSION}-plugins-base-vorbis \
	gstreamer${GST_VERSION}-plugins-base-rawparse \
	"

GST_GOOD_RDEPS = "\
	gstreamer${GST_VERSION}-plugins-good-amrnb \
	gstreamer${GST_VERSION}-plugins-good-amrwbdec \
	gstreamer${GST_VERSION}-plugins-good-apetag \
	gstreamer${GST_VERSION}-plugins-good-audioparsers \
	gstreamer${GST_VERSION}-plugins-good-autodetect \
	gstreamer${GST_VERSION}-plugins-good-avi \
	gstreamer${GST_VERSION}-plugins-good-flac \
	gstreamer${GST_VERSION}-plugins-good-flv \
	gstreamer${GST_VERSION}-plugins-good-icydemux \
	gstreamer${GST_VERSION}-plugins-good-id3demux \
	gstreamer${GST_VERSION}-plugins-good-isomp4 \
	gstreamer${GST_VERSION}-plugins-good-matroska \
	gstreamer${GST_VERSION}-plugins-good-mpg123 \
	gstreamer${GST_VERSION}-plugins-good-rtp \
	gstreamer${GST_VERSION}-plugins-good-rtpmanager \
	gstreamer${GST_VERSION}-plugins-good-rtsp \
	gstreamer${GST_VERSION}-plugins-good-soup \
	gstreamer${GST_VERSION}-plugins-good-udp \
	gstreamer${GST_VERSION}-plugins-good-wavparse \
	gstreamer${GST_VERSION}-plugins-good-wavpack \
	"

GST_BAD_RDEPS = "\
	gstreamer${GST_VERSION}-plugins-bad-autoconvert \
	gstreamer${GST_VERSION}-plugins-bad-dash \
	gstreamer${GST_VERSION}-plugins-bad-mpegpsdemux \
	gstreamer${GST_VERSION}-plugins-bad-mpegtsdemux \
	gstreamer${GST_VERSION}-plugins-bad-rtmp \
	gstreamer${GST_VERSION}-plugins-bad-smoothstreaming \
	gstreamer${GST_VERSION}-plugins-bad-faad \
	gstreamer${GST_VERSION}-plugins-bad-hls \
	gstreamer${GST_VERSION}-plugins-bad-opusparse \
	gstreamer${GST_VERSION}-plugins-bad-videoparsersbad \
	"

GST_UGLY_RDEPS = "\
	gstreamer${GST_VERSION}-plugins-ugly-asf \
	gstreamer${GST_VERSION}-plugins-ugly-cdio \
	gstreamer${GST_VERSION}-plugins-ugly-dvdsub \
	"

DEPENDS = "\
	enigma2 \
	gstreamer${GST_VERSION}-plugins-base gstreamer${GST_VERSION} \
	"

RDEPENDS:${PN} = "\
	enigma2 \
	"

RRECOMMENDS:${PN} = "\
	glib-networking \
	gstreamer${GST_VERSION}-plugin-subsink \
	${GST_BASE_RDEPS} \
	${GST_GOOD_RDEPS} \
	${GST_BAD_RDEPS} \
	${GST_UGLY_RDEPS} \
	"

SRC_URI = "git://github.com/openpli/servicemp3.git;protocol=https;branch=master"
SRC_URI:teamblue = "git://github.com/openpli/servicemp3.git;protocol=https;branch=python3 \
                    file://0001-fix-configure.ac.patch"
SRCREV = "${AUTOREV}"
SRCREV:openeight = "c7750c5ab393341f5a65094ceac90fbb6afd12e6"

S = "${WORKDIR}/git"

inherit autotools gitpkgv ${PYTHON_PN}native pkgconfig ${PYTHON_PN}targetconfig

PV = "git"
PKGV = "git${GITPKGV}"

EXTRA_OECONF = "\
	--with-gstversion=${GST_VERSION} \
	BUILD_SYS=${BUILD_SYS} \
	HOST_SYS=${HOST_SYS} \
	STAGING_INCDIR=${STAGING_INCDIR} \
	STAGING_LIBDIR=${STAGING_LIBDIR} \
	"

FILES:${PN} = "\
	${libdir}/enigma2/python/Plugins/SystemPlugins/ServiceMP3/*.pyc \
	${libdir}/enigma2/python/Plugins/SystemPlugins/ServiceMP3/*.pyo \
	${libdir}/enigma2/python/Plugins/SystemPlugins/ServiceMP3/servicemp3.so"

FILES:${PN}-dev = "\
	${libdir}/enigma2/python/Plugins/SystemPlugins/ServiceMP3/*.py \
	${libdir}/enigma2/python/Plugins/SystemPlugins/ServiceMP3/servicemp3.la"
