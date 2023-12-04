FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

PACKAGE_NO_LOCALE = "1"
SRC_URI:append = " \
           file://0009-glimagesink-Downrank-to-marginal.patch \
           file://0002-subparse-set-need_segment-after-sink-pad-received-GS.patch \
           file://0003-riff-media-added-fourcc-to-all-ffmpeg-mpeg4-video-caps.patch \
"

PACKAGECONFIG:append = " \
    cdparanoia gio opus tremor \
"

PACKAGECONFIG[gio]          = "-Dgio=enabled,-Dgio=disabled,glib-2.0"

# files installed by both gstreamer1.0-plugins-base and kodi
do_install:append() {
        rm -f ${D}${includedir}/KHR/khrplatform.h
        rm -f ${D}${includedir}/GL/glext.h
}

PV = "1.22.7"

SRC_URI[md5sum] = "156e377fb065b9ba5d38d30385f15608"
SRC_URI[sha256sum] = "62519e0d8f969ebf62a9a7996f2d23efdda330217a635f4a32c0bf1c71577468"
