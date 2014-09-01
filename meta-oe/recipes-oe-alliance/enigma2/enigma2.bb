SUMMARY = "Enigma2 is an experimental, but useful framebuffer-based frontend for DVB functions"
MAINTAINER = "OE-Alliance"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=751419260aa954499f7abaabaa882bbe"

DEPENDS = " \
    freetype \
    gettext-native \
    gst-plugins-base gstreamer \
    jpeg \
    libdreamdvd libdvbsi++ libfribidi libmad libpng libsigc++-1.2 libungif libxml2 libxmlccwrap \
    openssl \
    python python-imaging python-twisted python-wifi \
    swig-native \
    tuxtxt-enigma2 \
    ${@base_contains("TARGET_ARCH", "sh4", "libmmeimage " , "", d)} \
    "

RDEPENDS_${PN} = " \
    alsa-conf \
    enigma2-fonts \
    ethtool \
    glibc-gconv-iso8859-15 \
    hotplug-e2-helper \
    ${PYTHON_RDEPS} \
    "

RRECOMMENDS_${PN} = " \
    glib-networking \
    gst-plugin-subsink \
    ${@base_contains("TARGET_ARCH", "mipsel", "gst-plugin-libxt" , "", d)} \
    ${GST_BASE_RDEPS} \
    ${GST_GOOD_RDEPS} \
    ${GST_BAD_RDEPS} \
    ${GST_UGLY_RDEPS} \
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
    python-utf8-hack \
    python-xml \
    python-zlib \
    python-zopeinterface \
    python-email \
    python-mime \
    python-pyusb \
    python-subprocess \
    python-process \
    python-imaging \
    "

GST_BASE_RDEPS = " \
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
    gst-plugins-bad-fragmented \
    gst-plugins-bad-faad \
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

# DVD playback is integrated, we need the libraries
RDEPENDS_${PN} += "libdreamdvd"
RRECOMMENDS_${PN} += "libdvdcss"

# We depend on the font which we use for TXT subtitles (defined in skin_subtitles.xml)
RDEPENDS_${PN} += "font-valis-enigma"

RDEPENDS_${PN} += "${@base_contains("MACHINE_FEATURES", "blindscan-dvbc", "virtual/blindscan-dvbc" , "", d)}"

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

inherit autotools-brokensep gitpkgv pkgconfig pythonnative

PV = "3.0+git${SRCPV}"
PKGV = "3.0+git${GITPKGV}"
PR = "r14"

SRC_URI = "${ENIGMA2_URI}"

SRC_URI_append_azboxhd = " \
    file://azboxe2.patch \
    file://lcdchar.patch \
    file://e2_pcr.patch \
    file://add_more_timeout.patch \
    file://pic_show.patch \
    "
SRC_URI_append_azboxme = " \
    file://azboxe2.patch \
    file://e2_pcr.patch \
    file://add_more_timeout.patch \
    file://pic_show.patch \
    "
SRC_URI_append_azboxminime = " \
    file://azboxe2.patch \
    file://e2_pcr.patch \
    file://add_more_timeout.patch \
    file://pic_show.patch \
    "
SRC_URI_append_vuduo = " \
    file://duo_VFD.patch \
    "

S = "${WORKDIR}/git"

FILES_${PN} += "${datadir}/keymaps"
FILES_${PN}-meta = "${datadir}/meta"
PACKAGES =+ "${PN}-src"
PACKAGES += "${PN}-meta"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PACKAGES =+ "enigma2-fonts"
PV_enigma2-fonts = "2010.11.14"
PR_enigma2-fonts = "r0"
PKGV_enigma2-fonts = "${PV_enigma2-fonts}"
FILES_enigma2-fonts = "${datadir}/fonts"

EXTRA_OECONF = " \
    BUILD_SYS=${BUILD_SYS} \
    HOST_SYS=${HOST_SYS} \
    STAGING_INCDIR=${STAGING_INCDIR} \
    STAGING_LIBDIR=${STAGING_LIBDIR} \
    --with-boxtype=${MACHINE} \
    --with-po --with-libsdl=no \
    --enable-dependency-tracking \
    ${@base_contains("MACHINE_FEATURES", "textlcd", "--with-textlcd" , "", d)} \
    ${@base_contains("MACHINE_FEATURES", "colorlcd", "--with-colorlcd" , "", d)} \
    ${@base_contains("MACHINE_FEATURES", "colorlcd128", "--with-colorlcd128" , "", d)} \
    ${@base_contains("MACHINE_FEATURES", "colorlcd220", "--with-colorlcd220" , "", d)} \
    ${@base_contains("MACHINE_FEATURES", "colorlcd400", "--with-colorlcd400" , "", d)} \
    ${@base_contains("MACHINE_FEATURES", "bwlcd128", "--with-bwlcd128" , "", d)} \
    ${@base_contains("MACHINE_FEATURES", "bwlcd140", "--with-bwlcd140" , "", d)} \
    ${@base_contains("MACHINE_FEATURES", "bwlcd255", "--with-bwlcd255" , "", d)} \
    ${@base_contains("MACHINE_FEATURES", "fullgraphiclcd", "--with-fullgraphiclcd" , "", d)} \
    ${@base_contains("MACHINE_FEATURES", "gigabluelcd", "--with-gigabluelcd" , "", d)} \
    ${@base_contains("MACHINE_FEATURES", "nolcd", "--with-nolcd" , "", d)} \
    ${@base_contains("TARGET_ARCH", "sh4", "--enable-sh=yes " , "", d)} \
    "

# Swig generated 200k enigma.py file has no purpose for end users
FILES_${PN}-dbg += "\
    /usr/lib/enigma2/python/enigma.py \
    "

# some plugins contain so's, their stripped symbols should not end up in the enigma2 package
FILES_${PN}-dbg += "\
    /usr/lib/enigma2/python/*/.debug \
    /usr/lib/enigma2/python/*/*/*.debug \
    /usr/lib/enigma2/python/*/*/*/.debug \
    /usr/lib/enigma2/python/*/*/*/*/.debug \
    /usr/lib/enigma2/python/Plugins/*/*/.debug \
    "

# Save some space by not installing sources (mytest.py must remain)
FILES_${PN}-src = "\
    /usr/lib/enigma2/python/GlobalActions.py \
    /usr/lib/enigma2/python/Navigation.py \
    /usr/lib/enigma2/python/NavigationInstance.py \
    /usr/lib/enigma2/python/RecordTimer.py \
    /usr/lib/enigma2/python/ServiceReference.py \
    /usr/lib/enigma2/python/SleepTimer.py \
    /usr/lib/enigma2/python/e2reactor.py \
    /usr/lib/enigma2/python/keyids.py \
    /usr/lib/enigma2/python/keymapparser.py \
    /usr/lib/enigma2/python/skin.py \
    /usr/lib/enigma2/python/timer.py \
    /usr/lib/enigma2/python/upgrade.py \
    /usr/lib/enigma2/python/PowerTimer.py \
    /usr/lib/enigma2/python/*/*.py \
    /usr/lib/enigma2/python/*/*/*.py \
    /usr/lib/enigma2/python/*/*/*/*.py \
    "

FILES_${PN} += " ${bindir} ${sysconfdir}/e2-git.log"

# Save po files
PACKAGES =+ "${PN}-po"
FILES_${PN}-po = "${datadir}/enigma2/po/*.po ${datadir}/enigma2/po/*.pot"

do_install_append() {
    install -d ${D}/usr/share/keymaps
    find ${D}/usr/lib/enigma2/python/ -name '*.pyc' -exec rm {} \;
    ln -s /usr/lib/enigma2/python/Tools/StbHardware.pyo ${D}/usr/lib/enigma2/python/Tools/DreamboxHardware.pyo
    ln -s /usr/lib/enigma2/python/Components/PackageInfo.pyo ${D}/usr/lib/enigma2/python/Components/DreamboxInfoHandler.pyo
    install -d ${D}${sysconfdir}
    git --git-dir=${S}/.git log --since=10.weeks --pretty=format:"%s" > ${D}${sysconfdir}/e2-git.log
    git --git-dir=${OE-ALLIANCE_BASE}/.git log --since=10.weeks --pretty=format:"%s" > ${D}${sysconfdir}/oe-git.log
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
