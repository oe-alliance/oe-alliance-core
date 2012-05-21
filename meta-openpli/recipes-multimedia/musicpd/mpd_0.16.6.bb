DESCRIPTION = "Music Player Daemon (mpd)"
HOMEPAGE = "http://www.musicpd.org"
SECTION = "console/multimedia"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe"

DEPENDS = "libvorbis libogg libao zlib libmikmod flac audiofile virtual/libiconv faad2 curl \
           ${@base_conditional('ENTERPRISE_DISTRO', '1', '', 'libmad libid3tag lame', d)}"

PR = "r1"

SRC_URI = "${SOURCEFORGE_MIRROR}/musicpd/mpd-${PV}.tar.bz2 \
		file://mpd/mpd.conf \
		file://mpd/mpd.init \
		"

SRC_URI[md5sum] = "5489dd327fba12c67f01558d2cfa6d57"
SRC_URI[sha256sum] = "6ec5ad3fdeeb97608e40e041b6a458c61afd023a0fd1eeb464e208fcd36659df"

inherit autotools update-rc.d
INITSCRIPT_NAME = "mpd"

# Setting --enable-mpd-{mad,id3tag} causes local caches of the libraries to
# be built, instead we use the OE built versions which should be installed
# in staging - remove the --with and replace with --enable to use the local
# versions.

EXTRA_OECONF = "\
		--enable-ogg \
		--with-id3tag-libraries=${STAGING_LIBDIR} \
		--with-id3tag-includes=${STAGING_INCDIR} \
		--with-mad-libraries=${STAGING_LIBDIR} \
		--with-mad-includes=${STAGING_INCDIR} \
		--with-faad-libraries=${STAGING_LIBDIR} \
		--with-faad-includes=${STAGING_INCDIR} \
		--enable-curl \
		--disable-ffmpeg \
		--disable-jack \
		--disable-pulse \
		--enable-mod \
		--disable-oggflac \
		--with-lame-includes=${STAGING_INCDIR} \
		"

do_compile_prepend() {
	find -name Makefile | xargs sed -i 's~-I/usr/include~-I${STAGING_INCDIR}~g'
}

do_install_append() {
	install -d ${D}/var/lib/mpd/playlists
	install -d ${D}${sysconfdir}/init.d
	install -m 755 ${WORKDIR}/mpd/mpd.init ${D}${sysconfdir}/init.d/mpd
	install -m 644 ${WORKDIR}/mpd/mpd.conf ${D}${sysconfdir}/mpd.conf
}
