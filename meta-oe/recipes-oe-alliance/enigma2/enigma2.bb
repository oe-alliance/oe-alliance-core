SUMMARY = "Enigma2 is an experimental, but useful framebuffer-based frontend for DVB functions"
MAINTAINER = "OE-Alliance"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=751419260aa954499f7abaabaa882bbe"

DEPENDS = " \
    freetype \
    gettext-native \
    ${@bb.utils.contains("GST_VERSION", "1.0", "gstreamer1.0-plugins-base gstreamer1.0", "gst-plugins-base gstreamer", d)} \
    jpeg \
    libdreamdvd libdvbsi++ fribidi libmad libpng giflib libxml2 libxmlccwrap libsigc++-2.0 \
    openssl avahi libudfread \
    python python-imaging python-twisted python-wifi \
    swig-native \
    tuxtxt-enigma2 \
    ${@bb.utils.contains("DISTRO_NAME", "openspa", "uchardet" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "uianimation", "vuplus-libgles-${MACHINE} libvugles2" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "hiaccel", "dinobot-libs-${MACHINE}" , "", d)} \
    "

RDEPENDS_${PN} = " \
    alsa-conf \
    enigma2-fonts \
    ethtool \
    glibc-gconv-iso8859-15 \
    glibc-gconv-cp1250 \
    hotplug-e2-helper \
    ${PYTHON_RDEPS} \
    ${@bb.utils.contains("MACHINE_FEATURES", "uianimation", "vuplus-libgles-${MACHINE} libvugles2" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "hiaccel", "dinobot-libs-${MACHINE}" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "smallflash", "", "enigma2-plugin-font-wqy-microhei", d)} \
    oe-alliance-branding \
    "

RRECOMMENDS_${PN} = " \
    glib-networking \
    glibc-gconv-utf-16 \
    ${@bb.utils.contains("GST_VERSION", "1.0", "gstreamer1.0-plugin-subsink", "gst-plugin-subsink", d)} \
    ${GST_BASE_RDEPS} \
    ${GST_GOOD_RDEPS} \
    ${GST_BAD_RDEPS} \
    ${GST_UGLY_RDEPS} \
    ${@bb.utils.contains("GST_VERSION", "1.0", "${GST_BAD_OPUS}", "", d)} \
    "

PYTHON_RDEPS = " \
    python-codecs \
    python-core \
    python-crypt \
    python-fcntl \
    python-lang \
    python-netclient \
    python-netserver \
    python-pickle \
    python-re \
    python-shell \
    python-threading \
    python-twisted-core \
    python-twisted-web \
    python-xml \
    python-zlib \
    python-zopeinterface \
    python-email \
    python-mime \
    python-pyusb \
    python-subprocess \
    python-process \
    python-image \
    python-imaging \
    python-smtpd \
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
    ${@bb.utils.contains("TARGET_ARCH", "aarch64", " gstreamer1.0-plugins-base-opus gstreamer1.0-plugins-bad-opusparse", "", d)} \
    "

GST_UGLY_RDEPS = "${@bb.utils.contains('GST_VERSION', '1.0', ' \
    gstreamer1.0-plugins-ugly-amrnb \
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

# DVD playback is integrated, we need the libraries
RDEPENDS_${PN} += " \
    libdreamdvd \
    "

RRECOMMENDS_${PN} += "libdvdcss"

# We depend on the font which we use for TXT subtitles (defined in skin_subtitles.xml)
RDEPENDS_${PN} += "font-valis-enigma"

RDEPENDS_${PN} += "${@bb.utils.contains("MACHINE_FEATURES", "blindscan-dvbc", "virtual/blindscan-dvbc" , "", d)}"

#make sure default skin is installed.
RDEPENDS_${PN} += "${E2DEFAULTSKIN} "

DEMUXTOOL ?= "replex"

DESCRIPTION_append_enigma2-plugin-extensions-cutlisteditor = "enables you to cut your movies."
RDEPENDS_enigma2-plugin-extensions-cutlisteditor = "aio-grab"
DESCRIPTION_append_enigma2-plugin-extensions-graphmultiepg = "shows a graphical timeline EPG."
DESCRIPTION_append_enigma2-plugin-extensions-pictureplayer = "displays photos on the TV."
DESCRIPTION_append_enigma2-plugin-systemplugins-frontprocessorupdate = "keeps your frontprocessor up to date."
DESCRIPTION_append_enigma2-plugin-systemplugins-positionersetup = "helps you installing a motorized dish."
DESCRIPTION_append_enigma2-plugin-systemplugins-satelliteequipmentcontrol = "allows you to fine-tune DiSEqC-settings."
DESCRIPTION_append_enigma2-plugin-systemplugins-satfinder = "helps you to align your dish."
DESCRIPTION_append_enigma2-plugin-systemplugins-skinselector = "shows a menu with selectable skins."
DESCRIPTION_append_enigma2-plugin-systemplugins-videomode = "selects advanced video modes"
RDEPENDS_enigma2-plugin-systemplugins-nfiflash = "python-twisted-web"
RDEPENDS_enigma2-plugin-systemplugins-softwaremanager = "python-twisted-web"
DESCRIPTION_append_enigma2-plugin-systemplugins-crashlogautosubmit = "automatically send crashlogs to Dream Multimedia"
RDEPENDS_enigma2-plugin-systemplugins-crashlogautosubmit = "python-twisted-mail python-twisted-names python-compression python-mime python-email"
DESCRIPTION_append_enigma2-plugin-systemplugins-cleanupwizard = "informs you on low internal memory on system startup."
DESCRIPTION_append_enigma2-plugin-extensions-modem = "opens a menu to connect to internet via builtin modem."
RDEPENDS_enigma2-plugin-extensions-modem = "dreambox-modem-ppp-scripts"
DESCRIPTION_append_enigma2-plugin-systemplugins-wirelesslan = "helps you configuring your wireless lan"
RDEPENDS_enigma2-plugin-systemplugins-wirelesslan = "wpa-supplicant wireless-tools python-wifi"
DESCRIPTION_append_enigma2-plugin-systemplugins-networkwizard = "provides easy step by step network configuration"
# Note that these tools lack recipes
RDEPENDS_enigma2-plugin-extensions-dvdburn = "dvd+rw-tools dvdauthor mjpegtools cdrkit python-imaging ${DEMUXTOOL}"
RDEPENDS_enigma2-plugin-systemplugins-hotplug = "hotplug-e2-helper"
RDEPENDS_enigma2-plugin-systemplugins-fsblupdater = "python-distutils"
DESCRIPTION_enigma2-plugin-font-wqy-microhei = "Font wqy-microhei add support for China EPG"

inherit autotools-brokensep gitpkgv pkgconfig pythonnative upx-compress

PV = "${IMAGE_VERSION}+git${SRCPV}"
PKGV = "${IMAGE_VERSION}+git${GITPKGV}"

SRC_URI = "${ENIGMA2_URI}"

INSANE_SKIP_${PN} = "dev-deps"

SRC_URI_append_azboxhd = " \
    file://azboxe2.patch \
    file://lcdchar.patch \
    file://e2_pcr.patch \
    file://add_more_timeout.patch \
    file://pic_show.patch \
    ${@bb.utils.contains("DISTRO_NAME", "openatv", "file://azboxHDe2py.patch", "", d)} \
    "
SRC_URI_append_azboxme = " \
    file://azboxe2.patch \
    file://e2_pcr.patch \
    file://add_more_timeout.patch \
    file://pic_show.patch \
    ${@bb.utils.contains("DISTRO_NAME", "openatv", "file://azboxMEe2py.patch", "", d)} \
    "
SRC_URI_append_azboxminime = " \
    file://azboxe2.patch \
    file://e2_pcr.patch \
    file://add_more_timeout.patch \
    file://pic_show.patch \
    ${@bb.utils.contains("DISTRO_NAME", "openatv", "file://azboxMEe2py.patch", "", d)} \
    "
SRC_URI_append_vuduo = " \
    file://duo_VFD.patch \
    "

SRC_URI_append_egami = " \
    file://tuxbox_fix_DVB_API_VERSION_check_for_gcc5.patch \
    "

S = "${WORKDIR}/git"

FILES_${PN} += "${datadir}/keymaps"
FILES_${PN}-meta = "${datadir}/meta"
PACKAGES =+ "${PN}-src"
PACKAGES += "${PN}-meta"
PACKAGE_ARCH = "${MACHINEBUILD}"

PACKAGES =+ "enigma2-plugin-font-wqy-microhei enigma2-fonts"
FILES_enigma2-plugin-font-wqy-microhei = "${datadir}/fonts/wqy-microhei.ttc ${datadir}/fonts/fallback.font"
FILES_enigma2-fonts = "${datadir}/fonts"

ALLOW_EMPTY_enigma2-plugin-font-wqy-microhei = "1"

EXTRA_OECONF = " \
    BUILD_SYS=${BUILD_SYS} \
    HOST_SYS=${HOST_SYS} \
    STAGING_INCDIR=${STAGING_INCDIR} \
    STAGING_LIBDIR=${STAGING_LIBDIR} \
    --with-boxtype=${MACHINE} \
    --with-machinebuild="${MACHINEBUILD}" \
    --with-libsdl=no \
    --enable-dependency-tracking \
    ${@bb.utils.contains("GST_VERSION", "1.0", "--with-gstversion=1.0", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "textlcd", "--with-textlcd" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "colorlcd", "--with-colorlcd" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "colorlcd128", "--with-colorlcd128" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "colorlcd220", "--with-colorlcd220" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "colorlcd390", "--with-colorlcd390" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "colorlcd400", "--with-colorlcd400" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "colorlcd480", "--with-colorlcd480" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "colorlcd720", "--with-colorlcd720" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "colorlcd800", "--with-colorlcd800" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "bwlcd96", "--with-bwlcd96" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "bwlcd128", "--with-bwlcd128" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "bwlcd140", "--with-bwlcd140" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "bwlcd255", "--with-bwlcd255" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "fullgraphiclcd", "--with-fullgraphiclcd" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "gigabluelcd", "--with-gigabluelcd" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "nolcd", "--with-nolcd" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "7segment", "--with-7segment" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "uianimation", "--with-libvugles2" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "osdanimation", "--with-osdanimation" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "hiaccel", "--with-libhiaccel" , "", d)} \
    "

LDFLAGS_prepend = "${@bb.utils.contains('GST_VERSION', '1.0', ' -lxml2 ', '', d)}"

# Swig generated 200k enigma.py file has no purpose for end users
FILES_${PN}-dbg += "\
    ${libdir}/enigma2/python/enigma.py \
    "

# some plugins contain so's, their stripped symbols should not end up in the enigma2 package
FILES_${PN}-dbg += "\
    ${libdir}/enigma2/python/*/.debug \
    ${libdir}/enigma2/python/*/*/*.debug \
    ${libdir}/enigma2/python/*/*/*/.debug \
    ${libdir}/enigma2/python/*/*/*/*/.debug \
    ${libdir}/enigma2/python/Plugins/*/*/.debug \
    "

# Save some space by not installing sources (mytest.py must remain)
FILES_${PN}-src = "\
    ${libdir}/enigma2/python/GlobalActions.py \
    ${libdir}/enigma2/python/Navigation.py \
    ${libdir}/enigma2/python/NavigationInstance.py \
    ${libdir}/enigma2/python/RecordTimer.py \
    ${libdir}/enigma2/python/ServiceReference.py \
    ${libdir}/enigma2/python/SleepTimer.py \
    ${libdir}/enigma2/python/e2reactor.py \
    ${libdir}/enigma2/python/keyids.py \
    ${libdir}/enigma2/python/keymapparser.py \
    ${libdir}/enigma2/python/skin.py \
    ${libdir}/enigma2/python/timer.py \
    ${libdir}/enigma2/python/upgrade.py \
    ${libdir}/enigma2/python/PowerTimer.py \
    ${libdir}/enigma2/python/*/*.py \
    ${libdir}/enigma2/python/*/*/*.py \
    ${libdir}/enigma2/python/*/*/*/*.py \
    "

FILES_${PN} += " \
    ${bindir} ${sysconfdir}/e2-git.log /usr/lib"

# Save po files
PACKAGES =+ "${PN}-po"
FILES_${PN}-po = "${datadir}/enigma2/po/*.po ${datadir}/enigma2/po/*.pot"

do_install_append() {
    install -d ${D}/usr/share/keymaps
    find ${D}${libdir}/enigma2/python/ -name '*.pyc' -exec rm {} \;
    ln -s ${libdir}/enigma2/python/Tools/StbHardware.pyo ${D}${libdir}/enigma2/python/Tools/DreamboxHardware.pyo
    ln -s ${libdir}/enigma2/python/Components/PackageInfo.pyo ${D}${libdir}/enigma2/python/Components/DreamboxInfoHandler.pyo
    install -d ${D}${sysconfdir}
    git --git-dir=${S}/.git log --since=10.weeks --pretty=format:"%s" > ${D}${sysconfdir}/e2-git.log
    git --git-dir=${OE-ALLIANCE_BASE}/.git log --since=10.weeks --pretty=format:"%s" > ${D}${sysconfdir}/oe-git.log
    if [ "${base_libdir}" = "/lib64" ] ; then
        install -d ${D}/usr/lib
        ln -s ${libdir}/enigma2 ${D}/usr/lib/enigma2
        ln -s ${libdir}/python2.7 ${D}/usr/lib/python2.7
    fi
}

python populate_packages_prepend() {
    enigma2_plugindir = bb.data.expand('${libdir}/enigma2/python/Plugins', d)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/[a-zA-Z0-9_]+.*$', 'enigma2-plugin-%s', '%s', recursive=True, match_path=True, prepend=True, extra_depends="enigma2")
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.py$', 'enigma2-plugin-%s-src', '%s (source files)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.la$', 'enigma2-plugin-%s-dev', '%s (development)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.a$', 'enigma2-plugin-%s-staticdev', '%s (static development)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/(.*/)?\.debug/.*$', 'enigma2-plugin-%s-dbg', '%s (debug)', recursive=True, match_path=True, prepend=True)

    enigma2_podir = bb.data.expand('${datadir}/enigma2/po', d)
    do_split_packages(d, enigma2_podir, '^(\w+)/[a-zA-Z0-9_/]+.*$', 'enigma2-locale-%s', '%s', recursive=True, match_path=True, prepend=True, extra_depends="enigma2")
}

PACKAGES_DYNAMIC = "enigma2-plugin-* enigma2-locale-*"
