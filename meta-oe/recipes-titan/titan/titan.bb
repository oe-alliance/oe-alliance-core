SUMMARY = "TitanNit is a fast Linux Framebuffer Gui"
MAINTAINER = "TitanNit Team"
SECTION = "multimedia"
LICENSE = "GPLv2"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

inherit gitpkgv gettext

SRCREV = "${AUTOREV}"
PKGV = "2.0+svnr${GITPKGV}"
PV = "2.0+svnr${SRCPV}"
PR = "r3"

SRC_URI = "svn://sbnc.dyndns.tv/svn/;module=titan;protocol=http"

DEPENDS = " \
	curl \
	libpng \
	jpeg \
	rtmpdump \
	libdreamdvd \
	openssl \
	exteplayer3 \
	titan-libeplayer3 \
	${@bb.utils.contains("MACHINE_FEATURES", "uianimation", "vuplus-libgles-${MACHINE} libvugles2" , "", d)} \
	${@bb.utils.contains("MACHINE_FEATURES", "hiaccel", "dinobot-libs-${MACHINE}" , "", d)} \
	"

DEPENDS_append_sh4 = " \
	libmme-image \
	"

RDEPENDS_${PN} = " \
	glibc-gconv-iso8859-15 \
	hotplug-e2-helper \
	gawk \
	bash \
	${@bb.utils.contains("MACHINE_FEATURES", "uianimation", "vuplus-libgles-${MACHINE} libvugles2" , "", d)} \
	${@bb.utils.contains("MACHINE_FEATURES", "hiaccel", "dinobot-libs-${MACHINE}" , "", d)} \
	"

RRECOMMENDS_append_sh4_${PN} = " \
	libmme-host \
	"

RRECOMMENDS_append_arm_${PN} = " \
	glib-networking \
	glibc-gconv-utf-16 \
	${@bb.utils.contains("GST_VERSION", "1.0", "gstreamer1.0-plugins-base gstreamer1.0", "gst-plugins-base gstreamer", d)} \
	${@bb.utils.contains("GST_VERSION", "1.0", "gstreamer1.0-plugin-subsink", "gst-plugin-subsink", d)} \
	${@bb.utils.contains("GST_VERSION", "1.0", "gstreamer1.0-plugin-dvbmediasink", "gst-plugin-dvbmediasink", d)} \
	${GST_BASE_RDEPS} \
	${GST_GOOD_RDEPS} \
	${GST_BAD_RDEPS} \
	${GST_UGLY_RDEPS} \
	${GST_BASE_DVD} \
	${@bb.utils.contains("GST_VERSION", "1.0", "${GST_BAD_OPUS}", "", d)} \
	"

GST_BASE_RDEPS = "${@bb.utils.contains('GST_VERSION', '1.0', ' \
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
	gstreamer1.0-plugins-base-rawparse \
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

GST_GOOD_RDEPS = "${@bb.utils.contains('GST_VERSION', '1.0', ' \
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
	gstreamer1.0-plugins-good-soup \
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

GST_BAD_RDEPS = "${@bb.utils.contains('GST_VERSION', '1.0', ' \
	gstreamer1.0-plugins-bad-dashdemux \
	gstreamer1.0-plugins-bad-mms \
	gstreamer1.0-plugins-bad-mpegpsdemux \
	gstreamer1.0-plugins-bad-mpegtsdemux \
	gstreamer1.0-plugins-bad-rtmp \
	gstreamer1.0-plugins-bad-smoothstreaming \
	gstreamer1.0-plugins-bad-faad \
	gstreamer1.0-plugins-bad-hls \
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

GST_BAD_OPUS = " \
	${@bb.utils.contains("TARGET_ARCH", "arm", " gstreamer1.0-plugins-base-opus gstreamer1.0-plugins-bad-opusparse", "", d)} \
	"

GST_UGLY_RDEPS = "${@bb.utils.contains('GST_VERSION', '1.0', ' \
	gstreamer1.0-plugins-ugl42938y-amrnb \
	gstreamer1.0-plugins-ugly-amrwbdec \
	gstreamer1.0-plugins-ugly-asf \
	gstreamer1.0-plugins-ugly-cdio \
	gstreamer1.0-plugins-ugly-dvdsub \
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

GST_BASE_DVD = "${@bb.utils.contains('GST_VERSION', '1.0', ' \
	gstreamer1.0-plugins-bad-videoparsersbad \
	gstreamer1.0-plugins-bad-mpegtsmux \
	', ' \
	gst-plugins-bad-videoparsersbad \
	gst-plugins-bad-mpegtsmux \
	', d)}"

S = "${WORKDIR}/titan"

CFLAGS_append = " \
	-I${STAGING_DIR_TARGET}/usr/include \
	-I${STAGING_DIR_TARGET}/usr/include/freetype2 \
	-I${STAGING_DIR_TARGET}/usr/include/openssl \
	-I${STAGING_DIR_TARGET}/usr/include/dreamdvd \
	-I${STAGING_DIR_TARGET}/usr/include/libdreamdvd \
	-I${STAGING_DIR_TARGET}/usr/include/curl \
	-I${WORKDIR}/titan/libdreamdvd \
	-I${WORKDIR}/titan/titan \
	-I${WORKDIR}/titan/titan/include \
	-I${WORKDIR}/titan/libeplayer3/include \
	"

CFLAGS_append_arm = "${@bb.utils.contains('GST_VERSION', '1.0', ' \
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
	-I${STAGING_DIR_TARGET}/usr/include/libmmeimage \
	-I${STAGING_KERNEL_DIR}/extra/bpamem \
	"

CFLAGS_append_arm = " -DARM -DMIPSEL"
CFLAGS_append_arm_dm900 = " -DDREAMBOX -DCONFIG_ION"
CFLAGS_append_arm_dm920 = " -DDREAMBOX -DCONFIG_ION"
#CFLAGS_append_arm_mutant51 = " -DDREAMBOX -DCONFIG_ION"
#CFLAGS_append_arm_mutant60 = " -DDREAMBOX -DCONFIG_ION"

CFLAGS_append_mipsel = " -DMIPSEL"
CFLAGS_append_mipsel_dm7020hd = " -DDREAMBOX"
CFLAGS_append_mipsel_dm520 = " -DDREAMBOX -DCONFIG_ION"
CFLAGS_append_mipsel_dm525 = " -DDREAMBOX -DCONFIG_ION"

CFLAGS_append_sh4 = " -DOEBUILD -DEXTEPLAYER3 -DEPLAYER3 -DSH4 -DSH4NEW -DCAMSUPP -Os -export-dynamic -Wall -Wno-unused-but-set-variable -Wno-implicit-function-declaration"
CFLAGS_append_mipsel = " -DOEBUILD -DEXTEPLAYER3 -DEPLAYER3 -DCAMSUPP -Os -mhard-float -export-dynamic -Wall -Wno-unused-but-set-variable -Wno-implicit-function-declaration -Wno-unused-variable -Wno-format-overflow -Wno-format-truncation -Wno-nonnull -Wno-restrict"
CFLAGS_append_arm = " -DOEBUILD -DEXTEPLAYER3 -DEPLAYER3 -DCAMSUPP -Os -mhard-float -export-dynamic -Wall -Wno-unused-but-set-variable -Wno-implicit-function-declaration -Wno-unused-variable -Wno-format-overflow -Wno-format-truncation -Wno-nonnull -Wno-restrict"

#LDFLAGS_prepend = "${@bb.utils.contains('GST_VERSION', '1.0', ' -lglib-2.0 -lgobject-2.0 -lxml2 -lgstreamer-1.0 ', '', d)}"

LDFLAGS_prepend = " -leplayer3 -lpthread -ldl -lm -lz -lpng -lfreetype -ldreamdvd -ljpeg -lssl -lcrypto -lcurl "
LDFLAGS_prepend_sh4 = " -lmmeimage "

do_compile() {
	cd ${WORKDIR}/titan/titan/tools
	KERNELDIR=$HOME/flashimg/BUILDGIT/checkout_mips360/builds/titannit/release/${MACHINE}/tmp/deploy/images/${MACHINE}/uImage
	echo KERNELDIR: ${KERNELDIR}

	ROOTDIR=/home/obi/flashimg/BUILDGIT/checkout_mips360/builds/titannit/release/${MACHINE}/tmp/rootfs/${MACHINE}
	echo ROOTDIR: ${ROOTDIR}

	TYPE=${MACHINEBUILD}
	echo TYPE: ${TYPE}

	BOX=${MACHINEBUILD}
	echo BOX: ${BOX}

	SRCDIR=dummy
	echo SRCDIR: ${SRCDIR}

	CPU=${TARGET_ARCH}
	echo CPU: ${CPU}

	STM=mips360
	echo STM: ${STM}

	BOXNAME=${MACHINE}
	echo BOXNAME: ${BOXNAME}

	SWTYPE=titan
	echo SWTYPE: ${SWTYPE}

	echo IMAGE_NAME: ${IMAGE_NAME}

	SVNVERSION=`echo ${WORKDIR} | sed -nr 's/.*svnr([^.*]+)-.*/\1/p'`
	echo SVNVERSION: ${SVNVERSION}

	SVNVERSIONHTTP=`svn info http://sbnc.dyndns.tv/svn/titan | grep Revision | sed s/'Revision: '//g`
	echo SVNVERSIONHTTP: ${SVNVERSIONHTTP}

	GITVERSION=`git --git-dir=${OE-ALLIANCE_BASE}/.git log  --pretty=format:"%s" | wc -l`
	echo GITVERSION: ${GITVERSION}

	echo ./oealliance.sh ${KERNELDIR} ${ROOTDIR} ${TYPE} ${SRCDIR} ${CPU} ${STM} ${BOXNAME} ${DISTRO_NAME} ${DISTRO_TYPE} ${SWTYPE} ${IMAGE_NAME} ${GITVERSION} ${SVNVERSION}
	./oealliance.sh ${KERNELDIR} ${ROOTDIR} ${TYPE} ${SRCDIR} ${CPU} ${STM} ${BOXNAME} ${DISTRO_NAME} ${DISTRO_TYPE} ${SWTYPE} ${IMAGE_NAME} ${GITVERSION} ${SVNVERSION}

	cd ${WORKDIR}/titan/titan

	cp Makefile.am.4.3 Makefile.am

	libtoolize --force
	aclocal -I ${STAGING_DIR_TARGET}/usr/share/aclocal
	autoconf
	automake --foreign --add-missing
	./configure --host=${HOST_SYS} --build=${BUILD_SYS}

	make -f Makefile titan
}

FILES_${PN} = " \
	/bin \
	/etc \
	/etc/titan.restore \
	/etc/titan.restore/etc/udev \
	/etc/titan.restore/mnt/enigma2 \
	/etc/titan.restore/mnt/config \
	/etc/titan.restore/mnt/settings \
	/etc/titan.restore/mnt/network \
	/etc/titan.restore/var/etc/ipkg \
	/etc/titan.restore/var/etc/tuxbox \
	/etc/titan.restore/var/etc/titan \
	/etc/titan.restore/var/etc/boot \
	/etc/titan.restore/var/etc/autostart \
	/etc/titan.restore/var/etc/network \
	/etc/titan.restore/var/etc/ipkg \
	/etc/tpk.restore \
	/etc/tpk.restore/titan-plugin-infos-imdb \
	/etc/tpk.restore/titan-plugin-infos-imdbapi \
	/etc/tpk.restore/titan-plugin-infos-streaminfo \
	/etc/tpk.restore/titan-plugin-infos-tmdb \
	/etc/tpk.restore/titan-plugin-infos-weather \
	/etc/tpk.restore/titan-plugin-network-networkbrowser \
	/etc/tpk.restore/titan-plugin-player-mediacenter \
	/etc/tpk.restore/titan-plugin-player-tithek \
	/etc/tpk.restore/titan-plugin-tools-filemanager \
	/etc/init.d \
	/etc/mdev \
	/etc/network \
	/sbin \
	/usr/bin \
	/usr/local/bin \
	/var/etc \
	/var/etc/codepages \
	/var/etc/ipkg \
	/var/usr \
	/var/usr/local \
	/var/usr/share \
	/var/usr/local/share \
	/var/usr/local/share/titan \
	/var/usr/local/share/titan/web \
	/var/usr/local/share/titan/skin \
	/var/usr/local/share/titan/netsurf \
	/var/usr/local/share/titan/picons \
	/var/usr/local/share/titan/plugins \
	/var/usr/local/share/titan/help \
	/var/usr/local/share/titan/po \
	/var/usr/local/share/titan/web/tmp \
	/var/usr/local/share/titan/web/img \
	/var/usr/local/share/titan/skin/default \
	/var/usr/local/share/titan/skin/default/icons \
	/var/usr/local/share/titan/skin/default/infobar \
	/var/usr/local/share/titan/skin/default/png \
	/var/usr/local/share/titan/skin/default/oled \
	/var/usr/local/share/titan/skin/default/skin \
	/var/usr/local/share/titan/skin/default/preview \
	/var/usr/local/share/titan/netsurf/Choices \
	/var/usr/local/share/titan/netsurf/gtk \
	/var/usr/local/share/titan/netsurf/framebuffer \
	/var/usr/local/share/titan/netsurf/!NetSurf \
	/var/usr/local/share/titan/netsurf/gtk/res \
	/var/usr/local/share/titan/netsurf/gtk/res/icons \
	/var/usr/local/share/titan/netsurf/gtk/res/themes \
	/var/usr/local/share/titan/netsurf/gtk/res/de \
	/var/usr/local/share/titan/netsurf/gtk/res/en \
	/var/usr/local/share/titan/netsurf/gtk/res/throbber \
	/var/usr/local/share/titan/netsurf/gtk/res/ja \
	/var/usr/local/share/titan/netsurf/gtk/res/nl \
	/var/usr/local/share/titan/netsurf/gtk/res/fr \
	/var/usr/local/share/titan/netsurf/gtk/res/it \
	/var/usr/local/share/titan/netsurf/framebuffer/res \
	/var/usr/local/share/titan/netsurf/framebuffer/res/icons \
	/var/usr/local/share/titan/netsurf/framebuffer/res/fonts \
	/var/usr/local/share/titan/netsurf/framebuffer/res/throbber \
	/var/usr/local/share/titan/netsurf/framebuffer/res/pointers \
	/var/usr/local/share/titan/netsurf/!NetSurf/Docs \
	/var/usr/local/share/titan/netsurf/!NetSurf/Resources \
	/var/usr/local/share/titan/netsurf/!NetSurf/Resources/de \
	/var/usr/local/share/titan/netsurf/!NetSurf/Resources/en \
	/var/usr/local/share/titan/netsurf/!NetSurf/Resources/Icons \
	/var/usr/local/share/titan/netsurf/!NetSurf/Resources/ja \
	/var/usr/local/share/titan/netsurf/!NetSurf/Resources/nl \
	/var/usr/local/share/titan/netsurf/!NetSurf/Resources/Fonts \
	/var/usr/local/share/titan/netsurf/!NetSurf/Resources/fr \
	/var/usr/local/share/titan/netsurf/!NetSurf/Resources/it \
	/var/usr/local/share/titan/plugins/tithek \
	/var/usr/local/share/titan/plugins/readerconfig \
	/var/usr/local/share/titan/plugins/browser \
	/var/usr/local/share/titan/plugins/networkbrowser/skin \
	/var/usr/local/share/titan/plugins/imdbapi \
	/var/usr/local/share/titan/plugins/streaminfo \
	/var/usr/local/share/titan/plugins/mc/skin \
	/var/usr/local/share/titan/plugins/weather/skin \
	/var/usr/local/share/titan/plugins/tmdb \
	/var/usr/local/share/titan/plugins/gmediarender \
	/var/usr/local/share/titan/plugins/filemanager \
	/var/usr/local/share/titan/plugins/imdb \
	/var/usr/local/share/titan/help/de \
	/var/usr/local/share/titan/help/en \
	/var/usr/local/share/titan/po/pl \
	/var/usr/local/share/titan/po/lt \
	/var/usr/local/share/titan/po/de \
	/var/usr/local/share/titan/po/ru \
	/var/usr/local/share/titan/po/en \
	/var/usr/local/share/titan/po/el \
	/var/usr/local/share/titan/po/nl \
	/var/usr/local/share/titan/po/vi \
	/var/usr/local/share/titan/po/es \
	/var/usr/local/share/titan/po/fr \
	/var/usr/local/share/titan/po/it \
	/var/usr/share/fonts"


do_install() {
	install -d ${D}/usr/local/bin
	install -m 0755 titan/titan ${D}/usr/local/bin/titan

	cp -r oealliance/* ${D}
	if [ -e ${D}/etc/titan.restore/mnt/config/titan.${MACHINE}.cfg ];then
		cp ${D}/etc/titan.restore/mnt/config/titan.${MACHINE}.cfg ${D}/etc/titan.restore/mnt/config/titan.cfg
	fi
	if [ -e ${D}/etc/titan.restore/mnt/config/rcconfig.${MACHINE} ];then
		cp ${D}/etc/titan.restore/mnt/config/rcconfig.${MACHINE} ${D}/etc/titan.restore/mnt/config/rcconfig
	fi
}
do_install[vardepsexclude] += "DATETIME"


