SUMMARY = "TitanNit is a fast Linux Framebuffer Gui"
MAINTAINER = "TitanNit Team"
SECTION = "multimedia"
LICENSE = "GPLv2"
PACKAGE_ARCH = "${MACHINEBUILD}"

require conf/license/license-gplv2.inc

inherit autotools-brokensep gitpkgv python3native pkgconfig gettext

SRCREV = "${AUTOREV}"
PV = "${SRCPV}"

SRC_URI = "svn://buildbin:buildbin@sbnc.dyndns.tv;module=svn;protocol=http"

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
	titan-libipkg \
	gstreamer1.0-plugins-base gstreamer1.0 \
	gawk \
	"

DEPENDS:append:sh4 = " \
	libmme-image \
	"

RDEPENDS:${PN} = " \
	glibc-gconv-iso8859-15 \
	hotplug-e2-helper \
	gawk \
	bash \
	${@bb.utils.contains("MACHINE_FEATURES", "uianimation", "vuplus-libgles-${MACHINE} libvugles2" , "", d)} \
	${@bb.utils.contains("MACHINE_FEATURES", "hiaccel", "dinobot-libs-${MACHINE}" , "", d)} \
	"

RRECOMMENDS:append:sh4:${PN} = " \
	libmme-host \
	"

RRECOMMENDS:${PN} = " \
    glib-networking \
    glibc-gconv-utf-16 \
    gstreamer1.0-plugin-subsink \
    ${GST_BASE_RDEPS} \
    ${GST_GOOD_RDEPS} \
    ${GST_BAD_RDEPS} \
    ${GST_UGLY_RDEPS} \
    ${GST_BAD_OPUS} \
    "

GST_BASE_RDEPS = "\
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
"

GST_GOOD_RDEPS = "\
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
"

GST_BAD_RDEPS = "\
    gstreamer1.0-plugins-bad-dash \
    gstreamer1.0-plugins-bad-mms \
    gstreamer1.0-plugins-bad-mpegpsdemux \
    gstreamer1.0-plugins-bad-mpegtsdemux \
    gstreamer1.0-plugins-bad-rtmp \
    gstreamer1.0-plugins-bad-smoothstreaming \
    gstreamer1.0-plugins-bad-faad \
    gstreamer1.0-plugins-bad-hls \
    gstreamer1.0-plugins-bad-videoparsersbad \
    gstreamer1.0-plugins-bad-autoconvert \
"

GST_BAD_OPUS = " \
    ${@bb.utils.contains("TARGET_ARCH", "arm", " gstreamer1.0-plugins-base-opus gstreamer1.0-plugins-bad-opusparse", "", d)} \
    ${@bb.utils.contains("TARGET_ARCH", "aarch64", " gstreamer1.0-plugins-base-opus gstreamer1.0-plugins-bad-opusparse", "", d)} \
    "

GST_UGLY_RDEPS = "\
    gstreamer1.0-plugins-ugly-amrnb \
    gstreamer1.0-plugins-ugly-amrwbdec \
    gstreamer1.0-plugins-ugly-asf \
    gstreamer1.0-plugins-ugly-cdio \
    gstreamer1.0-plugins-ugly-dvdsub \
"

S = "${WORKDIR}/svn/titan/titan"

CFLAGS:append = " \
	-I${STAGING_DIR_TARGET}/usr/include \
	-I${STAGING_DIR_TARGET}/usr/include/freetype2 \
	-I${STAGING_DIR_TARGET}/usr/include/openssl \
	-I${STAGING_DIR_TARGET}/usr/include/dreamdvd \
	-I${STAGING_DIR_TARGET}/usr/include/libdreamdvd \
	-I${STAGING_DIR_TARGET}/usr/include/curl \
	-I${WORKDIR}/svn/titan/libdreamdvd \
	-I${WORKDIR}/svn/titan/titan \
	-I${WORKDIR}/svn/titan/titan/include \
	-I${WORKDIR}/svn/titan/libeplayer3/include \
	"


CFLAGS:append:arm = "${@bb.utils.contains('GST_VERSION', '1.0', ' \
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

CFLAGS:append:sh4 = " \
	-I${STAGING_DIR_TARGET}/usr/include/libmmeimage \
	-I${STAGING_KERNEL_DIR}/extra/bpamem \
	"

#CFLAGS:append = " -DDVDPLAYER"

CFLAGS:append:arm = " -DARM -DMIPSEL"
CFLAGS:append:arm:dm900 = " -DDREAMBOX -DCONFIG_ION"
CFLAGS:append:arm:dm920 = " -DDREAMBOX -DCONFIG_ION"
CFLAGS:append:arm:sf8008 = " -DCONFIG_HISILICON_FB"
#CFLAGS:append:arm:mutant51 = " -DDREAMBOX -DCONFIG_ION"
#CFLAGS:append:arm:mutant60 = " -DDREAMBOX -DCONFIG_ION"

CFLAGS:append:mipsel = " -DMIPSEL"
CFLAGS:append:mipsel:dm7020hd = " -DDREAMBOX"
CFLAGS:append:mipsel:dm520 = " -DDREAMBOX -DCONFIG_ION"
CFLAGS:append:mipsel:dm525 = " -DDREAMBOX -DCONFIG_ION"

CFLAGS:append:sh4 = " -DSSLNEW -DOEBUILD -DEXTEPLAYER3 -DEPLAYER3 -DSH4 -DSH4NEW -DCAMSUPP -Os -export-dynamic -Wall -Wno-unused-but-set-variable -Wno-implicit-function-declaration"
CFLAGS:append:mipsel = " -DSSLNEW -DOEBUILD -DEXTEPLAYER3 -DEPLAYER3 -DCAMSUPP -Os -mhard-float -export-dynamic -Wall -Wno-unused-but-set-variable -Wno-implicit-function-declaration -Wno-unused-variable -Wno-format-overflow -Wno-format-truncation -Wno-nonnull -Wno-restrict"
CFLAGS:append:arm = " -DSSLNEW -DOEBUILD -DEXTGST -DEPLAYER4 -DEXTEPLAYER3 -DEPLAYER3 -DCAMSUPP -Os -mhard-float -export-dynamic -Wall -Wno-unused-but-set-variable -Wno-implicit-function-declaration -Wno-unused-variable -Wno-format-overflow -Wno-format-truncation -Wno-nonnull -Wno-restrict"

LDFLAGS:prepend:arm = "${@bb.utils.contains('GST_VERSION', '1.0', ' -lglib-2.0 -lgobject-2.0 -lxml2 -lgstreamer-1.0 ', '', d)}"

LDFLAGS:prepend = " -leplayer3 -lpthread -ldl -lm -lz -lpng -lfreetype -ldreamdvd -ljpeg -lssl -lcrypto -lcurl -lipkg "
LDFLAGS:prepend:sh4 = " -lmmeimage "

do_configure:prepend() {
    cd ${S}/tools

    if [ "${MACHINE}" = "vusolo4k" -o "${MACHINE}" = "vusolo2" -o "${MACHINE}" = "vusolose" -o "${MACHINE}" = "vuduo2" -o "${MACHINE}" = "vuuno4k" -o "${MACHINE}" = "vuuno4kse" -o "${MACHINE}" = "vuultimo4k" -o "${MACHINE}" = "vuzero4k" -o "${MACHINE}" = "vuduo4k" -o "${MACHINE}" = "vuduo4kse" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-VUPLUS-BASE}/recipes-drivers/vuplus-dvb-proxy-${MACHINE}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "vuplus" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-VUPLUS-BASE}/recipes-drivers/vuplus-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "amiko" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-AMIKO-BASE}/recipes-drivers/amiko-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "beyonwiz" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-BEYONWIZ-BASE}/recipes-drivers/beyonwiz-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "clap" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-CLAP-BASE}/recipes-drivers/clap-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "fulan" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-FULAN-BASE}/recipes-drivers/fulan-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "LinkDroid" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-LINKDROID-BASE}/recipes-drivers/linkdroid-stb-${MACHINE}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "xtrend" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-XTREND-BASE}/recipes-drivers/et-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "entwopia" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-ENTWOPIA-BASE}/recipes-drivers/entwopia-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "dinobot" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-DINOBOT-BASE}/recipes-drivers/dinobot-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "dags" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-DAGS-BASE}/recipes-drivers/dags-dvb-modules-${MACHINE_DRIVER}.bb | cut -b 12-19`
    elif [ "${MACHINE}" = "gb7252" -o "${MACHINE}" = "gb72604" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-GIGABLUE-BASE}/recipes-drivers/gigablue-platform-util-${MACHINE_DRIVER}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "gigablue" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-GIGABLUE-BASE}/recipes-drivers/gigablue-dvb-modules-${MACHINE_DRIVER}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "odin" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-ODIN-BASE}/recipes-drivers/odin-dvb-modules-${MACHINE_DRIVER}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "octagon" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-OCTAGON-BASE}/recipes-drivers/octagon-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "uclan" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-UCLAN-BASE}/recipes-drivers/uclan-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "ini" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-INI-BASE}/recipes-drivers/ini-dvb-modules-${MACHINE_DRIVER}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "xp" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-XP-BASE}/recipes-drivers/xp-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "cube" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-CUBE-BASE}/recipes-drivers/e2bmc-dvb-modules-cube.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "ebox" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-EBOX-BASE}/recipes-drivers/ebox-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "ixuss" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-IXUSS-BASE}/recipes-drivers/ixuss-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "azbox" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-AZBOX-BASE}/recipes-drivers/azbox-dvb-modules.bb | cut -b 12-19`
    elif [ "${MACHINE}" = "blackbox7405" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-BLACKBOX-BASE}/recipes-drivers/blackbox-dvb-modules-blackbox7405.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "formuler" ]; then
        if [ "${MACHINE}" = "formuler1" ] || [ "${MACHINE}" = "formuler3" ] || [ "${MACHINE}" = "formuler4" ]; then
            DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-FORMULER-BASE}/recipes-drivers/formuler-dvb-modules-${MACHINE}.bb | cut -b 12-19`
        else
            DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-FORMULER-BASE}/recipes-drivers/formuler-dvb-modules-al-${MACHINE}.bb | cut -b 12-19`
        fi
    elif [ "${BRAND_OEM}" = "skylake" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-SKYLAKE-BASE}/recipes-drivers/skylake-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "tiviar" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-TIVIAR-BASE}/recipes-drivers/tiviar-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "gfutures" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-GFUTURES-BASE}/recipes-drivers/gfutures-dvb-modules-${MACHINE_DRIVER}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "tripledot" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-TRIPLEDOT-BASE}/recipes-drivers/tripledot-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "airdigital" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-AIRDIGITAL-BASE}/recipes-drivers/airdigital-dvb-modules-${MACHINE_DRIVER}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "ceryon" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-CERYON-BASE}/recipes-drivers/ceryon-dvb-modules-${MACHINE_DRIVER}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "broadmedia" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-BROADMEDIA-BASE}/recipes-drivers/broadmedia-dvb-modules-${MACHINE_DRIVER}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "xcore" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-XCORE-BASE}/recipes-drivers/xcore-dvb-modules-${MACHINE_DRIVER}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "ax" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-AX-BASE}/recipes-drivers/ax-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "ultramini" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-ULTRAMINI-BASE}/recipes-drivers/ultramini-dvb-modules-${MACHINE_DRIVER}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "wetek" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-WETEK-BASE}/recipes-drivers/wetek-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "protek" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-PROTEK-BASE}/recipes-drivers/protek-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "edision" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-EDISION-BASE}/recipes-drivers/edision-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "maxytec" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-MAXYTEC-BASE}/recipes-drivers/maxytec-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "dreambox" ]; then
        if [ "${MACHINE}" = "dm7080" ]; then
            DRIVERSDATE="20190502"
        elif [ "${MACHINE}" = "dm820" ]; then
            DRIVERSDATE="20181018"
        elif [ "${MACHINE}" = "dm520" ]; then
            DRIVERSDATE="20180222"
        elif [ "${MACHINE}" = "dm800" ]; then
            DRIVERSDATE="20131228"
        elif [ "${MACHINE}" = "dm8000" ]; then
            DRIVERSDATE="20140604"
        elif [ "${MACHINE}" = "dm7020hd" ] || [ "${MACHINE}" = "dm7020hdv2" ]; then
            DRIVERSDATE="20161019"
        elif [ "${MACHINE}" = "dm800se" ]; then
            DRIVERSDATE="20151201"
        elif [ "${MACHINE}" = "dm800sev2" ]; then
            DRIVERSDATE="20151201"
        elif [ "${MACHINE}" = "dm900" ]; then
            DRIVERSDATE="20200226"
        elif [ "${MACHINE}" = "dm920" ]; then
            DRIVERSDATE="20190830"
        else
            DRIVERSDATE="20150618"
        fi
    else
        DRIVERSDATE='N/A'
    fi

#	CACHEDIR=$(echo ${TMPDIR} | sed "s!builds/${DISTRO_NAME}/${DISTRO_TYPE}/${MACHINE}/tmp!svncache!")
	CACHEDIR=${TMPDIR}/svncache/${MACHINEBUILD}
	echo "CACHEDIR ${CACHEDIR}"

	KERNELDIR=${TMPDIR}/deploy/images/${MACHINE}/uImage
	echo "KERNELDIR: ${KERNELDIR}"

	ROOTDIR=${TMPDIR}/rootfs/${MACHINE}
	echo "ROOTDIR: ${ROOTDIR}"

	TYPE="${MACHINEBUILD}"
	echo "TYPE: ${TYPE}"

	BOX=${MACHINEBUILD}
	echo "BOX: ${BOX}"

	SRCDIR="dummy"
	echo "SRCDIR: ${SRCDIR}"

	CPU="${TARGET_ARCH}"
	echo "CPU: ${CPU}"

	STM="mips360"
	echo "STM: ${STM}"

	BOXNAME="${MACHINE}"
	echo "BOXNAME: ${BOXNAME}"

	SWTYPE="titan"
	echo "SWTYPE: ${SWTYPE}"

	echo "IMAGE_NAME: ${IMAGE_NAME}"

#	SVNVERSION=$(echo ${WORKDIR} | sed -nr 's/.*svnr([^.*]+)-.*/\1/p')
    SVNVERSION=${SRCPV}
	echo "SVNVERSION: ${SVNVERSION}"

	SVNVERSIONHTTP=$(svn info http://sbnc.dyndns.tv/svn/titan | grep Revision | sed s/'Revision: '//g)
	echo "SVNVERSIONHTTP: ${SVNVERSIONHTTP}"

	GITVERSION=$(git --git-dir=${OE-ALLIANCE_BASE}/.git log  --pretty=format:"%s" | wc -l)
	echo "GITVERSION: ${GITVERSION}"

	MACHINE_BRAND="${MACHINE_BRAND}"
	echo "MACHINE_BRAND: ${MACHINE_BRAND}"

	MACHINE_NAME="${MACHINE_NAME}"
	echo "MACHINE_NAME: ${MACHINE_NAME}"

	DRIVERSDATE="${DRIVERSDATE}"
	echo "DRIVERSDATE: ${DRIVERSDATE}"

	DISTRO_VERSION="${DISTRO_VERSION}"
	echo "DISTRO_VERSION: ${DISTRO_VERSION}"

	DISTRO_TYPE="${DISTRO_TYPE}"
	echo "DISTRO_TYPE: ${DISTRO_TYPE}"

	echo "./oealliance.sh ${CACHEDIR} ${KERNELDIR} ${ROOTDIR} ${TYPE} ${SRCDIR} ${CPU} ${STM} ${BOXNAME} ${DISTRO_NAME} ${DISTRO_TYPE} ${SWTYPE} ${IMAGE_NAME} ${GITVERSION} ${SVNVERSION} ${MACHINE_BRAND} ${MACHINE_NAME} ${DRIVERSDATE} ${DISTRO_VERSION} ${DISTRO_TYPE}"
	./oealliance.sh "${CACHEDIR}" "${KERNELDIR}" "${ROOTDIR}" "${TYPE}" "${SRCDIR}" "${CPU}" "${STM}" "${BOXNAME}" "${DISTRO_NAME}" "${DISTRO_TYPE}" "${SWTYPE}" "${IMAGE_NAME}" "${GITVERSION}" "${SVNVERSION}" "${MACHINE_BRAND}" "${MACHINE_NAME}" "${DRIVERSDATE}" "${DISTRO_VERSION}" "${DISTRO_TYPE}"

	cd ${S}
	cp Makefile.am.4.3 Makefile.am
	cd ${S}
}

EXTRA_OECONF = " \
    BUILD_SYS=${BUILD_SYS} \
    HOST_SYS=${HOST_SYS} \
    STAGING_INCDIR=${STAGING_INCDIR} \
    STAGING_LIBDIR=${STAGING_LIBDIR} \
"

FILES:${PN} = " \
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

INSANE_SKIP:${PN} = "already-stripped"

do_install() {
	install -d ${D}/usr/local/bin
	install -m 0755 titan ${D}/usr/local/bin/titan

	cp -r ../oealliance/* ${D}
	if [ -e ${D}/etc/titan.restore/mnt/config/titan.${MACHINE}.cfg ];then
		cp ${D}/etc/titan.restore/mnt/config/titan.${MACHINE}.cfg ${D}/etc/titan.restore/mnt/config/titan.cfg
	fi
	if [ -e ${D}/etc/titan.restore/mnt/config/rcconfig.${MACHINE} ];then
		cp ${D}/etc/titan.restore/mnt/config/rcconfig.${MACHINE} ${D}/etc/titan.restore/mnt/config/rcconfig
	fi
}
#do_install[vardepsexclude] += "DATETIME"

#do_configure[nostamp] = "1"
do_install[vardepsexclude] += "DATE"
