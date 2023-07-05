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

PV = "1.22.4"

SRC_URI[md5sum] = "7d495970b0b7078c50d064d228b8d93f"
SRC_URI[sha256sum] = "292424e82dea170528c42b456f62a89532bcabc0508f192e34672fb86f68e5b8"
