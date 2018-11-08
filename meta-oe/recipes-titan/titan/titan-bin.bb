SUMMARY = "TitanNit is a fast Linux Framebuffer Gui"
MAINTAINER = "TitanNit Team"
SECTION = "multimedia"
LICENSE = "GPLv2"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

inherit gitpkgv

SRCREV = "${AUTOREV}"
PKGV = "2.0+git${GITPKGV}"
PV = "2.0+svnr${SRCPV}"
PR = "r3"

SRC_URI = "svn://sbnc.dyndns.tv/svn/;module=titan;protocol=http"

DEPENDS = " \
	libpng \
	jpeg \
	rtmpdump \
	libdreamdvd \
	exteplayer3 \
	titan-libeplayer3 \
    "

RDEPENDS_${PN} = " \
    glibc-gconv-iso8859-15 \
    hotplug-e2-helper \
    "

RRECOMMENDS_append_mipsel_${PN} = " \
    glib-networking \
    ${@base_contains("GST_VERSION", "1.0", "gstreamer1.0-plugins-base gstreamer1.0", "gst-plugins-base gstreamer", d)} \
    ${@base_contains("GST_VERSION", "1.0", "gstreamer1.0-plugin-subsink", "gst-plugin-subsink", d)} \
    ${@base_contains("GST_VERSION", "1.0", "gstreamer1.0-plugin-dvbmediasink", "gst-plugin-dvbmediasink", d)} \
    ${GST_BASE_RDEPS} \
    ${GST_GOOD_RDEPS} \
    ${GST_BAD_RDEPS} \
    ${GST_UGLY_RDEPS} \
    ${GST_BASE_DVD} \
    "

RRECOMMENDS_append_sh4_${PN} = " \
    glib-networking \
	libmmeimage \
	libmmehost \
	"

GST_BASE_RDEPS = "${@base_contains('GST_VERSION', '1.0', ' \
    gstreamer1.0-plugins-base-alsa \
    gstreamer1.0-plugins-base-app \
    gstreamer1.0-plugins-base-audioconvert \
    gstreamer1.0-plugins-base-audioresample \
    gstreamer1.0-plugins-base-audiorate \
    gstreamer1.0-plugins-base-videoconvert \
    gstreamer1.0-plugins-base-ivorbisdec \
    gstreamer1.0-plugins-base-ogg \
    gstreamer1.0-plugins-base-playback \
    gstreamer1.0-plugins-base-subparse \
    gstreamer1.0-plugins-base-typefindfunctions \
    gstreamer1.0-plugins-base-vorbis \
    ', ' \
    gst-plugins-base-alsa \
    gst-plugins-base-app \
    gst-plugins-base-audioconvert \
    gst-plugins-base-audioresample \
    gst-plugins-base-decodebin \
    gst-plugins-base-decodebin2 \
    gst-plugins-base-ogg \
    gst-plugins-base-playbin \
    gst-plugins-base-subparse \
    gst-plugins-base-typefindfunctions \
    gst-plugins-base-vorbis \
    ', d)}"

GST_GOOD_RDEPS = "${@base_contains('GST_VERSION', '1.0', ' \
    gstreamer1.0-plugins-good-apetag \
    gstreamer1.0-plugins-good-audioparsers \
    gstreamer1.0-plugins-good-autodetect \
    gstreamer1.0-plugins-good-avi \
    gstreamer1.0-plugins-good-flac \
    gstreamer1.0-plugins-good-flv \
    gstreamer1.0-plugins-good-icydemux \
    gstreamer1.0-plugins-good-id3demux \
    gstreamer1.0-plugins-good-isomp4 \
    gstreamer1.0-plugins-good-matroska \
    gstreamer1.0-plugins-good-rtp \
    gstreamer1.0-plugins-good-rtpmanager \
    gstreamer1.0-plugins-good-rtsp \
    gstreamer1.0-plugins-good-souphttpsrc \
    gstreamer1.0-plugins-good-udp \
    gstreamer1.0-plugins-good-wavparse \
    gstreamer1.0-plugins-good-wavpack \
    ', ' \
    gst-plugins-good-apetag \
    gst-plugins-good-audioparsers \
    gst-plugins-good-autodetect \
    gst-plugins-good-avi \
    gst-plugins-good-flac \
    gst-plugins-good-flv \
    gst-plugins-good-icydemux \
    gst-plugins-good-id3demux \
    gst-plugins-good-isomp4 \
    gst-plugins-good-matroska \
    gst-plugins-good-rtp \
    gst-plugins-good-rtpmanager \
    gst-plugins-good-rtsp \
    gst-plugins-good-souphttpsrc \
    gst-plugins-good-udp \
    gst-plugins-good-wavparse \
    ', d)}"

GST_BAD_RDEPS = "${@base_contains('GST_VERSION', '1.0', ' \
    gstreamer1.0-plugins-bad-dashdemux \
    gstreamer1.0-plugins-bad-mms \
    gstreamer1.0-plugins-bad-mpegpsdemux \
    gstreamer1.0-plugins-bad-mpegtsdemux \
    gstreamer1.0-plugins-bad-rtmp \
	gstreamer1.0-plugins-bad-smoothstreaming \
    gstreamer1.0-plugins-bad-faad \
    gstreamer1.0-plugins-bad-fragmented \
    gstreamer1.0-plugins-bad-videoparsersbad \
    gstreamer1.0-plugins-bad-autoconvert \
    ', ' \
    gst-plugins-bad-cdxaparse \
    gst-plugins-bad-mms \
    gst-plugins-bad-mpegdemux \
    gst-plugins-bad-rtmp \
    gst-plugins-bad-vcdsrc \
    gst-plugins-bad-fragmented \
    gst-plugins-bad-faad \
    ', d)}"

GST_UGLY_RDEPS = "${@base_contains('GST_VERSION', '1.0', ' \
    gstreamer1.0-plugins-ugly-amrnb \
    gstreamer1.0-plugins-ugly-amrwbdec \
    gstreamer1.0-plugins-ugly-asf \
    gstreamer1.0-plugins-ugly-cdio \
    gstreamer1.0-plugins-ugly-dvdsub \
    gstreamer1.0-plugins-ugly-mad \
    ', ' \
    gst-plugins-ugly-amrnb \
    gst-plugins-ugly-amrwbdec \
    gst-plugins-ugly-asf \
    gst-plugins-ugly-cdio \
    gst-plugins-ugly-dvdsub \
    gst-plugins-ugly-mad \
    gst-plugins-ugly-mpegaudioparse \
    gst-plugins-ugly-mpegstream \
    ', d)}"

GST_BASE_DVD = "${@base_contains('GST_VERSION', '1.0', ' \
    gstreamer1.0-plugins-bad-videoparsersbad \
    gstreamer1.0-plugins-bad-mpegtsmux \
    ', ' \
    gst-plugins-bad-videoparsersbad \
    gst-plugins-bad-mpegtsmux \
    ', d)}"

S = "${WORKDIR}/titan"

CFLAGS_append_mipsel = "${@base_contains('GST_VERSION', '1.0', ' \
	-I${STAGING_DIR_TARGET}/usr/include \
	-I${STAGING_DIR_TARGET}/usr/lib/gstreamer-1.0/include \
	-I${STAGING_DIR_TARGET}/usr/include/gstreamer-1.0 \
	-I${STAGING_DIR_TARGET}/usr/include/glib-2.0 \
	-I${STAGING_DIR_TARGET}/usr/include/libxml2 \
	-I${STAGING_DIR_TARGET}/usr/lib/glib-2.0/include \
	-I${STAGING_DIR_TARGET}/usr/include/freetype2 \
	-I${STAGING_DIR_TARGET}/usr/include/dreamdvd \
	-I${STAGING_DIR_TARGET}/usr/include/libdreamdvd \	
	-I${WORKDIR}/titan/libdreamdvd \
	-I${WORKDIR}/titan/titan \
    ', ' \
	-I${STAGING_DIR_TARGET}/usr/include \
	-I${STAGING_DIR_TARGET}/usr/include/gstreamer-0.10 \
	-I${STAGING_DIR_TARGET}/usr/include/glib-2.0 \
	-I${STAGING_DIR_TARGET}/usr/include/libxml2 \
	-I${STAGING_DIR_TARGET}/usr/lib/glib-2.0/include \
	-I${STAGING_DIR_TARGET}/usr/include/freetype2 \
	-I${STAGING_DIR_TARGET}/usr/include/dreamdvd \
	-I${STAGING_DIR_TARGET}/usr/include/libdreamdvd \	
	-I${WORKDIR}/titan/libdreamdvd \
	-I${WORKDIR}/titan/titan \
    ', d)}"

CFLAGS_append_sh4 = " \
	-I${STAGING_DIR_TARGET}/usr/include \
	-I${STAGING_DIR_TARGET}/usr/include/freetype2 \
	-I${STAGING_DIR_TARGET}/usr/include/openssl \
	-I${STAGING_DIR_TARGET}/usr/include/libmmeimage \
	-I${STAGING_DIR_TARGET}/usr/include/libeplayer3/include \
	-I${STAGING_KERNEL_DIR}/extra/bpamem \
	-I${WORKDIR}/titan/libdreamdvd \
	-I${WORKDIR}/titan/titan \
	"

do_compile() {
	cd ${WORKDIR}/titan/titan

	SVNVERSION=`svn info http://sbnc.dyndns.tv/svn/titan | grep Revision | sed s/'Revision: '//g`
	echo SVNVERSION: ${SVNVERSION}

#    svn update

	if [ ${HOST_SYS} = "sh4-oe-linux" ];then
		cp Makefile.am.sh4 Makefile.am
	elif [ ${HOST_SYS} = "arm-oe-linux-gnueabi" ];then
		cp Makefile.am.arm Makefile.am
	else
		cp Makefile.am.mipsel Makefile.am
	fi

	libtoolize --force
	aclocal -I ${STAGING_DIR_TARGET}/usr/share/aclocal
	autoconf
	automake --foreign --add-missing
	./configure --host=${HOST_SYS} --build=${BUILD_SYS}

	make -f Makefile titan
    ${STRIP} titan
}

FILES_${PN} = "/usr/local/bin"

do_install_append() {
    install -d ${D}/usr/local/bin
    install -m 0755 titan/titan ${D}/usr/local/bin/titan   
}
