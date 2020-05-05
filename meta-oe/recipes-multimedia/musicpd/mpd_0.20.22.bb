SUMMARY = "Music Player Daemon (mpd)"
HOMEPAGE = "http://www.musicpd.org"
SECTION = "console/multimedia"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe"

DEPENDS = "audiofile boost curl dbus expat faad2 flac icu libao libmikmod libogg libvorbis sqlite3 virtual/libiconv yajl zlib \
           ${@oe.utils.conditional('ENTERPRISE_DISTRO', '1', '', 'lame libid3tag libmad', d)}"


inherit gitpkgv

SRCREV = "9274bc15bc41bbe490fde847f8422468cc20375d"
PV = "0.20.22"
PKGV = "0.20.22"

SRC_URI = "git://github.com/MusicPlayerDaemon/MPD \
        file://mpd.conf \
        file://mpd.init \
        "

S = "${WORKDIR}/git"

inherit autotools pkgconfig update-rc.d

INITSCRIPT_NAME = "mpd"

PACKAGECONFIG ??= "alsa ao bzip2 daemon ffmpeg fifo flac fluidsynth iso9660 jack libsamplerate libwrap httpd mms mpg123 modplug sndfile upnp openal opus oss recorder vorbis wavpack zlib"
PACKAGECONFIG += "${@bb.utils.contains('LICENSE_FLAGS', 'commercial', 'aac', '', d)}"

PACKAGECONFIG[aac] = "--enable-aac,--disable-aac,faad2"
PACKAGECONFIG[alsa] = "--enable-alsa,--disable-alsa,alsa-lib"
PACKAGECONFIG[ao] = "--enable-ao,--disable-ao,libao"
PACKAGECONFIG[audiofile] = "--enable-audiofile,--disable-audiofile,audiofile"
PACKAGECONFIG[bzip2] = "--enable-bzip2,--disable-bzip2,bzip2"
PACKAGECONFIG[cdioparanoia] = "--enable-cdio-paranoia,--disable-cdio-paranoia,libcdio-paranoia"
PACKAGECONFIG[daemon] = "--enable-daemon,--disable-daemon"
PACKAGECONFIG[ffmpeg] = "--enable-ffmpeg,--disable-ffmpeg,ffmpeg"
PACKAGECONFIG[fifo] = "--enable-fifo,--disable-fifo"
PACKAGECONFIG[flac] = "--enable-flac,--disable-flac,flac"
PACKAGECONFIG[fluidsynth] = "--enable-fluidsynth,--disable-fluidsynth,fluidsynth"
PACKAGECONFIG[httpd] = "--enable-httpd-output,--disable-httpd-output"
PACKAGECONFIG[id3tag] = "--enable-id3,--disable-id3,libid3tag"
PACKAGECONFIG[iso9660] = "--enable-iso9660,--disable-iso9660,libcdio"
PACKAGECONFIG[jack] = "--enable-jack,--disable-jack,jack"
PACKAGECONFIG[lame] = "--enable-lame-encoder,--disable-lame-encoder,lame"
PACKAGECONFIG[libsamplerate] = "--enable-lsr,--disable-lsr,libsamplerate0"
PACKAGECONFIG[libwrap] = "--enable-libwrap,--disable-libwrap,tcp-wrappers"
PACKAGECONFIG[mad] = "--enable-mad,--disable-mad,libmad"
PACKAGECONFIG[mms] = "--enable-mms,--disable-mms,libmms"
PACKAGECONFIG[modplug] = "--enable-modplug,--disable-modplug,libmodplug"
PACKAGECONFIG[mpg123] = "--enable-mpg123,--disable-mpg123,mpg123"
PACKAGECONFIG[openal] = "--enable-openal,--disable-openal,openal-soft"
PACKAGECONFIG[opus] = "--enable-opus,--disable-opus,libopus libogg"
PACKAGECONFIG[oss] = "--enable-oss,--disable-oss,"
PACKAGECONFIG[recorder] = "--enable-recorder-output,--disable-recorder-output"
PACKAGECONFIG[smb] = "--enable-smbclient,--disable-smbclient,samba"
PACKAGECONFIG[sndfile] = "--enable-sndfile,--disable-sndfile,libsndfile1"
PACKAGECONFIG[upnp] = "--enable-upnp,--disable-upnp,libupnp"
PACKAGECONFIG[vorbis] = "--enable-vorbis,--disable-vorbis,libvorbis libogg"
PACKAGECONFIG[wavpack] = "--enable-wavpack,--disable-wavpack,wavpack"
PACKAGECONFIG[zlib] = "--enable-zlib,--disable-zlib,zlib"

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

    # we don't need the icons
    rm -rf ${D}${datadir}/icons
}
