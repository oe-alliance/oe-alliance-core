SUMMARY = "Enigma2 is an experimental, but useful framebuffer-based frontend for DVB functions"
MAINTAINER = "OE-Alliance"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=751419260aa954499f7abaabaa882bbe"
LIC_FILES_CHKSUM_teamblue = "file://LICENSE;md5=b234ee4d69f5fce4486a80fdaf4a4263"
LIC_FILES_CHKSUM_openatv = "file://LICENSE;md5=b234ee4d69f5fce4486a80fdaf4a4263"
LIC_FILES_CHKSUM_openvix = "file://LICENSE;md5=b234ee4d69f5fce4486a80fdaf4a4263"
LIC_FILES_CHKSUM_beyonwiz = "file://LICENSE;md5=b234ee4d69f5fce4486a80fdaf4a4263"
LIC_FILES_CHKSUM_openeight = "file://LICENSE;md5=b234ee4d69f5fce4486a80fdaf4a4263"
LIC_FILES_CHKSUM_opendroid = "file://LICENSE;md5=b234ee4d69f5fce4486a80fdaf4a4263"

DEPENDS = " \
    freetype \
    gettext-native \
    gstreamer1.0-plugins-base gstreamer1.0 \
    jpeg \
    libdreamdvd libdvbsi++ fribidi libmad libpng giflib libxml2 libxmlccwrap libsigc++-2.0 \
    openssl avahi libudfread \
    ${PYTHON_PN} ${@bb.utils.contains("PYTHON_PN", "python", "${PYTHON_PN}-imaging", "${PYTHON_PN}-pillow", d)} ${PYTHON_PN}-twisted ${PYTHON_PN}-wifi ${PYTHON_PN}-six-native \
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
    ${@bb.utils.contains("DISTRO_NAME", "openvix", "image-identifier" , "", d)} \
    ${@bb.utils.contains("DISTRO_NAME", "openatv", "openatv-autorestore" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "uianimation", "vuplus-libgles-${MACHINE} libvugles2" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "hiaccel", "dinobot-libs-${MACHINE}" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "smallflash", "", "enigma2-plugin-font-wqy-microhei", d)} \
    oe-alliance-branding \
    "

RRECOMMENDS_${PN} = " \
    glib-networking \
    glibc-gconv-utf-16 \
    gstreamer1.0-plugin-subsink \
    ${GST_BASE_RDEPS} \
    ${GST_GOOD_RDEPS} \
    ${GST_BAD_RDEPS} \
    ${GST_UGLY_RDEPS} \
    ${GST_BAD_OPUS} \
    "

PYTHON_RDEPS = " \
    ${PYTHON_PN}-codecs \
    ${PYTHON_PN}-core \
    ${PYTHON_PN}-crypt \
    ${PYTHON_PN}-fcntl \
    ${@bb.utils.contains("PYTHON_PN", "python", "${PYTHON_PN}-lang", "", d)} \
    ${PYTHON_PN}-mmap \
    ${PYTHON_PN}-netclient \
    ${PYTHON_PN}-netifaces \
    ${PYTHON_PN}-netserver \
    ${PYTHON_PN}-pickle \
    ${@bb.utils.contains("PYTHON_PN", "python", "${PYTHON_PN}-re", "", d)} \
    ${PYTHON_PN}-shell \
    ${PYTHON_PN}-threading \
    ${PYTHON_PN}-twisted-core \
    ${PYTHON_PN}-twisted-web \
    ${PYTHON_PN}-xml \
    ${@bb.utils.contains("PYTHON_PN", "python", "${PYTHON_PN}-zlib", "", d)} \
    ${PYTHON_PN}-zopeinterface \
    ${PYTHON_PN}-email \
    ${PYTHON_PN}-mime \
    ${PYTHON_PN}-pyusb \
    ${@bb.utils.contains("PYTHON_PN", "python", "${PYTHON_PN}-subprocess", "", d)} \
    ${PYTHON_PN}-process \
    ${PYTHON_PN}-image \
    ${@bb.utils.contains("PYTHON_PN", "python", "${PYTHON_PN}-imaging", "${PYTHON_PN}-pillow", d)} \
    ${PYTHON_PN}-smtpd \
    ${PYTHON_PN}-six \
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
RDEPENDS_enigma2-plugin-systemplugins-nfiflash = "${PYTHON_PN}-twisted-web"
RDEPENDS_enigma2-plugin-systemplugins-softwaremanager = "${PYTHON_PN}-twisted-web"
DESCRIPTION_append_enigma2-plugin-systemplugins-crashlogautosubmit = "automatically send crashlogs to Dream Multimedia"
RDEPENDS_enigma2-plugin-systemplugins-crashlogautosubmit = "${PYTHON_PN}-twisted-mail ${PYTHON_PN}-twisted-names ${PYTHON_PN}-compression ${PYTHON_PN}-mime ${PYTHON_PN}-email"
DESCRIPTION_append_enigma2-plugin-systemplugins-cleanupwizard = "informs you on low internal memory on system startup."
DESCRIPTION_append_enigma2-plugin-extensions-modem = "opens a menu to connect to internet via builtin modem."
RDEPENDS_enigma2-plugin-extensions-modem = "dreambox-modem-ppp-scripts"
DESCRIPTION_append_enigma2-plugin-systemplugins-wirelesslan = "helps you configuring your wireless lan"
RDEPENDS_enigma2-plugin-systemplugins-wirelesslan = "wpa-supplicant wireless-tools ${PYTHON_PN}-wifi"
DESCRIPTION_append_enigma2-plugin-systemplugins-networkwizard = "provides easy step by step network configuration"
# Note that these tools lack recipes
RDEPENDS_enigma2-plugin-extensions-dvdburn = "dvd+rw-tools dvdauthor mjpegtools cdrkit ${PYTHON_PN}-imaging ${DEMUXTOOL}"
RDEPENDS_enigma2-plugin-systemplugins-hotplug = "hotplug-e2-helper"
RDEPENDS_enigma2-plugin-systemplugins-fsblupdater = "${PYTHON_PN}-distutils"
DESCRIPTION_enigma2-plugin-font-wqy-microhei = "Font wqy-microhei add support for China EPG"

inherit autotools-brokensep gitpkgv pkgconfig ${PYTHON_PN}native upx-compress

PV = "${IMAGE_VERSION}+git${SRCPV}"
PKGV = "${IMAGE_VERSION}+git${GITPKGV}"

SRC_URI = "${ENIGMA2_URI}"

SRC_URI_append_u42 = " \
    file://enigma2-dinobotplayer.patch \
    "

SRC_URI_append_sh4 = " \
    ${@bb.utils.contains("DISTRO_NAME", "openspa", "file://sh4-define-DTV_ENUM_DELSYS.patch" , "", d)} \
    "

SRC_URI_append_vuduo = " \
    file://duo_VFD.patch \
    "

S = "${WORKDIR}/git"

FILES_${PN} += "${datadir}/keymaps"
FILES_${PN}-meta = "${datadir}/meta"
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
    --with-gstversion=1.0 \
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
SRC_URI_append = "${@bb.utils.contains("MACHINE_FEATURES", "uianimation", " file://use-lv3ddriver.patch" , "", d)}"


# some plugins contain so's, their stripped symbols should not end up in the enigma2 package
FILES_${PN}-dbg += "\
    ${libdir}/enigma2/python/*/.debug \
    ${libdir}/enigma2/python/*/*/*.debug \
    ${libdir}/enigma2/python/*/*/*/.debug \
    ${libdir}/enigma2/python/*/*/*/*/.debug \
    ${libdir}/enigma2/python/Plugins/*/*/.debug \
    "

FILES_${PN} += " \
    ${bindir} ${sysconfdir}/e2-git.log /usr/lib"

# Save po files
PACKAGES =+ "${PN}-po"
FILES_${PN}-po = "${datadir}/enigma2/po/*.po ${datadir}/enigma2/po/*.pot"

do_install_append() {
    install -d ${D}/usr/share/keymaps
    ln -s ${libdir}/enigma2/python/Tools/StbHardware.py ${D}${libdir}/enigma2/python/Tools/DreamboxHardware.py
    ln -s ${libdir}/enigma2/python/Components/PackageInfo.py ${D}${libdir}/enigma2/python/Components/DreamboxInfoHandler.py
    install -d ${D}${sysconfdir}
    git --git-dir=${S}/.git log --no-merges --since=10.weeks --pretty=format:"%s" > ${D}${sysconfdir}/e2-git.log
    git --git-dir=${OE-ALLIANCE_BASE}/.git log --no-merges --since=10.weeks --pretty=format:"%s" > ${D}${sysconfdir}/oe-git.log
    if [ "${base_libdir}" = "/lib64" ] ; then
        install -d ${D}/usr/lib
        ln -s ${libdir}/enigma2 ${D}/usr/lib/enigma2
        ln -s ${libdir}/${PYTHON_DIR} ${D}/usr/lib/${PYTHON_DIR}
    fi
}

python populate_packages_prepend() {
    enigma2_plugindir = bb.data.expand('${libdir}/enigma2/python/Plugins', d)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/[a-zA-Z0-9_]+.*$', 'enigma2-plugin-%s', '%s', recursive=True, match_path=True, prepend=True, extra_depends="enigma2")
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.la$', 'enigma2-plugin-%s-dev', '%s (development)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.a$', 'enigma2-plugin-%s-staticdev', '%s (static development)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/(.*/)?\.debug/.*$', 'enigma2-plugin-%s-dbg', '%s (debug)', recursive=True, match_path=True, prepend=True)

    enigma2_podir = bb.data.expand('${datadir}/enigma2/po', d)
    do_split_packages(d, enigma2_podir, '^(\w+)/[a-zA-Z0-9_/]+.*$', 'enigma2-locale-%s', '%s', recursive=True, match_path=True, prepend=True, extra_depends="enigma2")
}

do_package_qa() {
}
