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

CFLAGS:append = "${@' -mthumb' if d.getVar('TARGET_ARCH') == 'arm' else ''}"

PACKAGECONFIG[gio]          = "-Dgio=enabled,-Dgio=disabled,glib-2.0"

# files installed by both gstreamer1.0-plugins-base and kodi
do_install:append() {
        rm -f ${D}${includedir}/KHR/khrplatform.h
        rm -f ${D}${includedir}/GL/glext.h
}

INSANE_SKIP:libgstgl-1.0 += "file-rdeps"

PV = "1.28.6"
 
SRC_URI[sha256sum] = "0ba699c7c6c66f4ba640be78cb38a24715add9683f3e3a199f5369dc5a4f04ac"
