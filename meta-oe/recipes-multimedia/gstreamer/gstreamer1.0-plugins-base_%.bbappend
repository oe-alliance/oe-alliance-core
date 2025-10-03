FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

PACKAGE_NO_LOCALE = "1"
SRC_URI:append = " \
           file://0009-glimagesink-Downrank-to-marginal.patch \
           file://0002-subparse-set-need_segment-after-sink-pad-received-GS.patch \
           file://0003-riff-media-added-fourcc-to-all-ffmpeg-mpeg4-video-caps.patch \
		   file://0004-add-missing-mesa-define.patch \
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

INSANE_SKIP:libgstgl-1.0 += "file-rdeps"

PV = "1.26.6"
SRC_URI[sha256sum] = "6e13c83be9f7746d7a5b7dbbb44882f214c3afcf32946c9a1f1bab33cc3da5a1"
