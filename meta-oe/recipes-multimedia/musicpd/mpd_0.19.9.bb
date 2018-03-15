SUMMARY = "Music Player Daemon (mpd)"
HOMEPAGE = "http://www.musicpd.org"
SECTION = "console/multimedia"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe"

DEPENDS = "libvorbis libogg libao zlib libmikmod flac audiofile virtual/libiconv faad2 curl icu boost \
           ${@oe.utils.conditional('ENTERPRISE_DISTRO', '1', '', 'libmad libid3tag lame', d)}"

inherit gitpkgv
SRCREV = "5761800197a86ba6de70af026546e678cbda4b91"
PV = "0.19.9"
PKGV = "0.19.9"

SRC_URI = "git://github.com/MusicPlayerDaemon/MPD.git;protocol=git \
        file://mpd.conf \
        file://mpd.init \
        file://gcc61.patch \
        "

S = "${WORKDIR}/git"

inherit autotools pkgconfig update-rc.d
INITSCRIPT_NAME = "mpd"

# Setting --enable-mpd-{mad,id3tag} causes local caches of the libraries to
# be built, instead we use the OE built versions which should be installed
# in staging - remove the --with and replace with --enable to use the local
# versions.

EXTRA_OECONF = "\
        --enable-curl \
        --disable-ffmpeg \
        --disable-jack \
        --disable-pulse \
        "

do_compile_prepend() {
    find -name Makefile | xargs sed -i 's~-I/usr/include~-I${STAGING_INCDIR}~g'
}

do_install_append() {
    install -d ${D}/var/lib/mpd/playlists
    install -d ${D}${sysconfdir}/init.d
    install -m 755 ${WORKDIR}/mpd.init ${D}${sysconfdir}/init.d/mpd
    install -m 644 ${WORKDIR}/mpd.conf ${D}${sysconfdir}/mpd.conf
}
