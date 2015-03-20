FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
DEPENDS += "libdca"
EXTRA_OECONF := "${@bb.data.getVar('EXTRA_OECONF',d,1).replace('--disable-dts', '--enable-dts --enable-mpegdemux')}"
PACKAGECONFIG += "uvch264 neon bz2 faac faad libmms hls dash smoothstreaming webp rtmp orc curl sndfile directfb"
SRC_URI += "file://0001-rtmp-fix-seeking-and-potential-segfault.patch"

require mips-only.inc

