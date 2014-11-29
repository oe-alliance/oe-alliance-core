#PR="r15"
FILESEXTRAPATHS_prepend := "${THISDIR}:"
DEPENDS += "librtmp libdca"
EXTRA_OECONF := "${@bb.data.getVar('EXTRA_OECONF',d,1).replace('--disable-rtmp', '--enable-rtmp --enable-mpegdemux').replace('--disable-dts', '--enable-dts')}"
PACKAGECONFIG += "faac faad libmms hls dash smoothstreaming webp"

SRC_URI += "file://0001-rtmp-fix-seeking-and-potential-segfault.patch"

require mips-only.inc

# Do not strip binary
#INHIBIT_PACKAGE_STRIP = "1"
#do_strip[noexec] = "1"
#do_strip="no"
#do_strip(){
#        pass
#}

