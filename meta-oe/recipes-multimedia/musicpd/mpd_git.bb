SUMMARY = "Music Player Daemon (mpd)"
HOMEPAGE = "http://www.musicpd.org"
SECTION = "multimedia"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe"

DEPENDS += "audiofile boost curl dbus expat faad2 flac fmt icu libao \
            libmikmod libogg libvorbis sqlite3 virtual/libiconv yajl zlib"

inherit gitpkgv

SRCREV = "3242943cd11dbf47117b448a4d2f8e5053cc5681"
PV = "0.23.11+git${SRCPV}"
PKGV = "0.23.11+git${GITPKGV}"

SRC_URI = "git://github.com/MusicPlayerDaemon/MPD;branch=master;protocol=https \
        file://mpd.conf \
        file://mpd.init \
        "

S = "${WORKDIR}/git"

inherit meson pkgconfig update-rc.d

INITSCRIPT_NAME = "mpd"

PACKAGECONFIG = "${@bb.utils.contains('LICENSE_FLAGS_ACCEPTED', 'commercial', 'aac', '', d)} \
                 alsa ao bzip2 curl daemon \
                 fifo flac fluidsynth id3tag iso9660 lame mad \
                 libsamplerate httpd \
                 mms mpg123 modplug sndfile \
                 upnp openal opus oss recorder \
                 vorbis wavpack zlib"

PACKAGECONFIG[aac] = "-Dfaad=enabled,-Dfaad=disabled,faad2"
PACKAGECONFIG[alsa] = "-Dalsa=enabled,-Dalsa=disabled,alsa-lib"
PACKAGECONFIG[ao] = "-Dao=enabled,-Dao=disabled,libao"
PACKAGECONFIG[audiofile] = "-Daudiofile=enabled,-Daudiofile=disabled,audiofile"
PACKAGECONFIG[bzip2] = "-Dbzip2=enabled,-Dbzip2=disabled,bzip2"
PACKAGECONFIG[cdioparanoia] = "-Dcdio_paranoia=enabled,-Dcdio_paranoia=disabled,libcdio-paranoia"
PACKAGECONFIG[daemon] = "-Ddaemon=true,-Ddaemon=false"
PACKAGECONFIG[curl] = "-Dcurl=enabled,-Dcurl=disabled,curl"
PACKAGECONFIG[ffmpeg] = "-Dffmpeg=enabled,-Dffmpeg=disabled,ffmpeg"
PACKAGECONFIG[fifo] = "-Dfifo=true,-Dfifo=false"
PACKAGECONFIG[flac] = "-Dflac=enabled,-Dflac=disabled,flac"
PACKAGECONFIG[fluidsynth] = "-Dfluidsynth=enabled,-Dfluidsynth=disabled,fluidsynth"
PACKAGECONFIG[httpd] = "-Dhttpd=true,-Dhttpd=false"
PACKAGECONFIG[id3tag] = "-Did3tag=enabled,-Did3tag=disabled,libid3tag"
PACKAGECONFIG[iso9660] = "-Diso9660=enabled,-Diso9660=disabled,libcdio"
PACKAGECONFIG[jack] = "-Djack=enabled,-Djack=disabled,jack"
PACKAGECONFIG[lame] = "-Dlame=enabled,-Dlame=disabled,lame"
PACKAGECONFIG[libsamplerate] = "-Dlibsamplerate=enabled,-Dlibsamplerate=disabled,libsamplerate0"
PACKAGECONFIG[mad] = "-Dmad=enabled,-Dmad=disabled,libmad"
PACKAGECONFIG[mms] = "-Dmms=enabled,-Dmms=disabled,libmms"
PACKAGECONFIG[modplug] = "-Dmodplug=enabled,-Dmodplug=disabled,libmodplug"
PACKAGECONFIG[mpg123] = "-Dmpg123=enabled,-Dmpg123=disabled,mpg123"
PACKAGECONFIG[openal] = "-Dopenal=enabled,-Dopenal=disabled,openal-soft"
PACKAGECONFIG[opus] = "-Dopus=enabled,-Dopus=disabled,libopus libogg"
PACKAGECONFIG[oss] = "-Doss=enabled,-Doss=disabled,"
PACKAGECONFIG[pulse] = "-Dpulse=enabled,-Dpulse=disabled,pulseaudio"
PACKAGECONFIG[recorder] = "-Drecorder=true,-Drecorder=false"
PACKAGECONFIG[smb] = "-Dsmbclient=enabled,-Dsmbclient=disabled,samba"
PACKAGECONFIG[sndfile] = "-Dsndfile=enabled,-Dsndfile=disabled,libsndfile1"
PACKAGECONFIG[upnp] = "-Dupnp=pupnp,-Dupnp=disabled,libupnp"
PACKAGECONFIG[vorbis] = "-Dvorbis=enabled,-Dvorbis=disabled,libvorbis libogg"
PACKAGECONFIG[wavpack] = "-Dwavpack=enabled,-Dwavpack=disabled,wavpack"
PACKAGECONFIG[zlib] = "-Dzlib=enabled,-Dzlib=disabled,zlib"

do_install:append() {
    install -d ${D}/var/lib/mpd/playlists
    install -d ${D}${sysconfdir}/init.d
    install -m 755 ${WORKDIR}/mpd.init ${D}${sysconfdir}/init.d/mpd
    install -m 644 ${WORKDIR}/mpd.conf ${D}${sysconfdir}/mpd.conf

    # we don't need the icons
    rm -rf ${D}${datadir}/icons
}
