SUMMARY = "StreamRipper lets you record streaming mp3 to your hard drive."
SECTION = "console/multimedia"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=8ca43cbc842c2336e835926c2166c28b"
DEPENDS = "libogg libvorbis faad2 glib-2.0 libmad"
PR = "r1"

SRC_URI = "${SOURCEFORGE_MIRROR}/streamripper/streamripper-${PV}.tar.gz"
SRC_URI[md5sum] = "a37a1a8b8f9228522196a122a1c2dd32"
SRC_URI[sha256sum] = "c1d75f2e9c7b38fd4695be66eff4533395248132f3cc61f375196403c4d8de42"

EXTRA_OECONF = "--disable-oggtest \
		--disable-vorbistest \
		--with-ogg=${STAGING_LIBDIR} \
		--with-vorbis=${STAGING_LIBDIR}"

inherit autotools
