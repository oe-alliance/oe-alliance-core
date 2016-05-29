SUMMARY = "neutrinoHD2 binary, freshly compiled"
#LICENSE = "GPLv3"
include conf/license/license-gplv2.inc

PV = "svn-2226"
PR = "r66"
SRCPV = "2226"

BOXTYPE = "UNKNOWN"

BOXTYPE_vuuno     = "vuplus"
BOXTYPE_vuultimo  = "vuplus"
BOXTYPE_vusolo    = "vuplus"
BOXTYPE_vuduo     = "vuplus"
BOXTYPE_vusolo2   = "vuplus"
BOXTYPE_vuduo2    = "vuplus"

BOXTYPE_et4x00    = "xtrend"
BOXTYPE_et5x00    = "xtrend"
BOXTYPE_et6x00    = "xtrend"
BOXTYPE_et9x00    = "xtrend"

BOXTYPE_tm2t      = "technomate"
BOXTYPE_tmsingle  = "technomate"
BOXTYPE_tmtwin    = "technomate"
BOXTYPE_tmnano    = "technomate"
BOXTYPE_tmnano2t    = "technomate"

BOXTYPE_azboxhd   = "azbox"
BOXTYPE_azboxme   = "azbox"
BOXTYPE_azboxminime = "azbox"

BOXTYPE_gb800se   = "gigablue"
BOXTYPE_gb800ue   = "gigablue"
BOXTYPE_gb800solo = "gigablue"
BOXTYPE_gb800seplus = "gigablue"
BOXTYPE_gb800ueplus = "gigablue"
BOXTYPE_gbquad    = "gigablue"

BOXTYPE_dm8000    = "dreambox"
BOXTYPE_dm7020hd  = "dreambox"
BOXTYPE_dm500hd   = "dreambox"
BOXTYPE_dm800se   = "dreambox"
BOXTYPE_dm800     = "dreambox"

BOXTYPE_maram9    = "odin"
BOXTYPE_odinm6    = "odin"
BOXTYPE_odinm7    = "odin"
BOXTYPE_odinm9    = "odin"

BOXTYPE_ventonhde = "venton"
BOXTYPE_ventonhdx = "venton"
BOXTYPE_inihde = "venton"
BOXTYPE_inihdp = "venton"
BOXTYPE_mbtwin = "venton"

BOXTYPE_xp1000    = "xp1000"

BOXTYPE_ebox5000  = "mixos"
BOXTYPE_ebox5100  = "mixos"
BOXTYPE_eboxlumi  = "mixos"
BOXTYPE_ebox7358  = "mixos"

BOXTYPE_e3hd = "e3hd"

BOXTYPE_ixusszero  = "ixuss"
BOXTYPE_ixussone  = "ixuss"

BOXTYPE_iqonios100hd  = "iqonios"
BOXTYPE_iqonios300hd  = "iqonios"
BOXTYPE_mediabox = "iqonios"
BOXTYPE_optimussos1 = "iqonios"
BOXTYPE_optimussos2 = "iqonios"

BOXMODEL = "${MACHINE}"

NHD2_MIPSEL_DEPENDS = " \
    gstreamer \
    gst-plugins-base \
    gst-plugin-subsink \
    libdreamdvd \
    python \
    "

NHD2ARCHDEPENDS = "${NHD2_MIPSEL_DEPENDS}"

DEPENDS = "\
    curl \
    ffmpeg \
    freetype \
    libid3tag \
    libpng \
    libungif \
    jpeg \
    libmad \
    libpng \
    libungif \
    openssl \
    graphlcd-base \
    \
    ${NHD2ARCHDEPENDS} \
    "
GST_BASE_RDEPS = " \
    gst-plugins-base-app \
    gst-plugins-base-audioconvert \
    gst-plugins-base-audioresample \
    gst-plugins-base-decodebin \
    gst-plugins-base-decodebin2 \
    gst-plugins-base-ivorbisdec \
    gst-plugins-base-ogg \
    gst-plugins-base-playbin \
    gst-plugins-base-subparse \
    gst-plugins-base-typefindfunctions \
    "

GST_GOOD_RDEPS = " \
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
    "

GST_BAD_RDEPS = " \
    gst-plugins-bad-cdxaparse \
    gst-plugins-bad-mms \
    gst-plugins-bad-mpegdemux \
    gst-plugins-bad-rtmp \
    gst-plugins-bad-vcdsrc \
    gst-plugins-bad-h264parse \
    "

GST_UGLY_RDEPS = " \
    gst-plugins-ugly-amrnb \
    gst-plugins-ugly-amrwbdec \
    gst-plugins-ugly-asf \
    gst-plugins-ugly-cdio \
    gst-plugins-ugly-dvdsub \
    gst-plugins-ugly-mad \
    gst-plugins-ugly-mpegaudioparse \
    gst-plugins-ugly-mpegstream \
    "

NHD2_MIPSEL_RDEPENDS = " \
    glibc-gconv-iso8859-15 \
    libdreamdvd \
    libpython2 \
    "

NHD2_MIPSEL_EXTRA_RDEPENDS = " \
    schroedinger \
    ${GST_BASE_RDEPS} \
    ${GST_GOOD_RDEPS} \
    ${GST_BAD_RDEPS} \
    ${GST_UGLY_RDEPS} \
    "

NHD2MACHINERDEPENDS = "${NHD2_MIPSEL_RDEPENDS}"
NHD2MACHINEEXTRARDEPENDS = "${NHD2_MIPSEL_EXTRA_RDEPENDS}"

RDEPENDS_${PN} = "\ 
    libavcodec \
    libavformat \
    libavutil \
    \
    ${NHD2MACHINERDEPENDS} \
    "

RREPLACES_${PN} = "neutrinohd-bin"
RCONFLICTS_${PN} = "neutrinohd-bin"
RPROVIDES_${PN} = "${NEUTRINOHDBIN}"

#INHIBIT_PACKAGE_STRIP = "1"

include neutrinohdurl.inc

do_fetch() {
    svn checkout http://neutrinohd2.googlecode.com/svn/branches/nhd2-exp/
}

S = "${WORKDIR}/${MODULE}"


inherit autotools pkgconfig

EXTRA_OECONF = "\
    --prefix=/ \
    --exec_prefix=/usr \
    --build=i686-pc-linux-gnu \
    --with-target=cdk \
    --enable-maintainer-mode \
    --without-debug \
    --with-bindir=/usr/bin \
    --with-sbindir=/usr/sbin \
    --with-libdir=/usr/lib \
    --with-gamesdir=/etc/tuxbox/games \
    --with-plugindir=/etc/tuxbox/plugins \
    --with-configdir=/etc/tuxbox/config \
    --enable-radiotext \
    --enable-freesatepg \
    --enable-upnp \
    --enable-ci \
    --enable-gstreamer \
    --enable-graphlcd \
    --enable-netzkino \
    --with-boxtype=${BOXTYPE} --with-boxmodel=${BOXMODEL} \
    ${@bb.utils.contains("MACHINE_FEATURES", "gigablue", "--enable-4digits" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "odin", "--enable-4digits" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "dreambox", "--enable-lcd" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "colorlcd220", "--enable-lcd" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "bwlcd140", "--enable-lcd" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "bwlcd255", "--enable-lcd" , "", d)} \
"

do_configure_append() {
    echo '#define SVNVERSION "${SRCPV}"' >> ${S}/src/gui/svn_version.h
}

