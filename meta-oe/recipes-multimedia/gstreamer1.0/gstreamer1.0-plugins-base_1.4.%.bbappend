FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
PACKAGECONFIG[cdparanoia]     = "--enable-cdparanoia,--disable-cdparanoia,cdparanoia"
PACKAGECONFIG += "cdparanoia ogg vorbis ivorbis alsa"
EXTRA_OECONF := "${@bb.data.getVar('EXTRA_OECONF',d,1).replace('--disable-cdparanoia', '--enable-cdparanoia')}"
SRC_URI += "file://0001-riff-media-added-fourcc-to-all-mpeg4-video-caps.patch"

require mips-only.inc

PR .= ".1"