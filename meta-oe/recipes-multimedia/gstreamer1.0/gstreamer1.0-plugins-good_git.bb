LICENSE = "GPLv2+ & LGPLv2.1+"
LIC_FILES_CHKSUM = "file://COPYING;md5=a6f89e2100d9b6cdffcea4f398e37343 \
                    file://gst/replaygain/rganalysis.c;beginline=1;endline=23;md5=b60ebefd5b2f5a8e0cab6bfee391a5fe \
"

require gstreamer1.0-plugins-common.inc

DEPENDS += "gstreamer1.0-plugins-base libcap zlib"

SRCREV_FORMAT = "gst_plugins_good"

SRC_URI = "git://gitlab.freedesktop.org/gstreamer/gst-plugins-good;protocol=https;branch=master;name=gst_plugins_good \
           file://0001-gstrtpmp4gpay-set-dafault-value-for-MPEG4-without-co.patch \
"

RPROVIDES_${PN}-pulseaudio += "${PN}-pulse"
RPROVIDES_${PN}-soup += "${PN}-souphttpsrc"

PACKAGECONFIG ??= " \
    ${GSTREAMER_ORC} \
    ${@bb.utils.filter('DISTRO_FEATURES', 'pulseaudio x11', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'novp9', '', 'vpx',d)} \
    bz2 cairo flac gdk-pixbuf gudev jpeg lame libpng mpg123 soup speex taglib v4l2 \
    wavpack \
"

X11DEPENDS = "virtual/libx11 libsm libxrender libxfixes libxdamage"

PACKAGECONFIG[bz2]        = "-Dbz2=enabled,-Dbz2=disabled,bzip2"
PACKAGECONFIG[cairo]      = "-Dcairo=enabled,-Dcairo=disabled,cairo"
PACKAGECONFIG[dv1394]     = "-Ddv1394=enabled,-Ddv1394=disabled,libiec61883 libavc1394 libraw1394"
PACKAGECONFIG[flac]       = "-Dflac=enabled,-Dflac=disabled,flac"
PACKAGECONFIG[gdk-pixbuf] = "-Dgdk-pixbuf=enabled,-Dgdk-pixbuf=disabled,gdk-pixbuf"
PACKAGECONFIG[gtk]        = "-Dgtk3=enabled,-Dgtk3=disabled,gtk+3"
PACKAGECONFIG[gudev]      = "-Dv4l2-gudev=enabled,-Dv4l2-gudev=disabled,libgudev"
PACKAGECONFIG[jack]       = "-Djack=enabled,-Djack=disabled,jack"
PACKAGECONFIG[jpeg]       = "-Djpeg=enabled,-Djpeg=disabled,jpeg"
PACKAGECONFIG[lame]       = "-Dlame=enabled,-Dlame=disabled,lame"
PACKAGECONFIG[libpng]     = "-Dpng=enabled,-Dpng=disabled,libpng"
PACKAGECONFIG[libv4l2]    = "-Dv4l2-libv4l2=enabled,-Dv4l2-libv4l2=disabled,v4l-utils"
PACKAGECONFIG[mpg123]     = "-Dmpg123=enabled,-Dmpg123=disabled,mpg123"
PACKAGECONFIG[pulseaudio] = "-Dpulse=enabled,-Dpulse=disabled,pulseaudio"
PACKAGECONFIG[soup]       = "-Dsoup=enabled,-Dsoup=disabled,libsoup-2.4"
PACKAGECONFIG[speex]      = "-Dspeex=enabled,-Dspeex=disabled,speex"
PACKAGECONFIG[taglib]     = "-Dtaglib=enabled,-Dtaglib=disabled,taglib"
PACKAGECONFIG[v4l2]       = "-Dv4l2=enabled -Dv4l2-probe=true,-Dv4l2=disabled -Dv4l2-probe=false"
PACKAGECONFIG[vpx]        = "-Dvpx=enabled,-Dvpx=disabled,libvpx"
PACKAGECONFIG[wavpack]    = "-Dwavpack=enabled,-Dwavpack=disabled,wavpack"
PACKAGECONFIG[x11]        = "-Dximagesrc=enabled -Dximagesrc-xfixes=enabled -Dximagesrc-xdamage=enabled \
                            -Dximagesrc-xshm=enabled,,${X11DEPENDS}"

# qt5 support is disabled, because it is not present in OE core, and requires more work than
# just adding a packageconfig (it requires access to moc, uic, rcc, and qmake paths).
# This is better done in a separate qt5 layer (which then should add a "qt5" packageconfig
# in a gstreamer1.0-plugins-good bbappend).

EXTRA_OEMESON += " \
    -Doss=enabled \
    -Daalib=disabled \
    -Ddirectsound=disabled \
    -Dlibcaca=disabled \
    -Doss4=disabled \
    -Dosxaudio=disabled \
    -Dosxvideo=disabled \
    -Dshout2=disabled \
    -Dwaveform=disabled \
"

FILES_${PN}-equalizer += "${datadir}/gstreamer-1.0/presets/*.prs"
