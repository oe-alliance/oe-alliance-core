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

PV = "1.24.8"
 
SRC_URI[md5sum] = "68b29aaae4de05e0c484ce7714ef5d0c"
SRC_URI[sha256sum] = "10fb31743750ccd498d3933e8aaecda563ebc65596a6ab875b47ee936e4b9599"
