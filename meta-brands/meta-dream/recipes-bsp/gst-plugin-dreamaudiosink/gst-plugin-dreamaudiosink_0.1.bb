SUMMARY = "GStreamer audio sink for Dreambox AMLogic boxes"
DESCRIPTION = "dreamaudiosink GStreamer 1.0 element for DreamOne / DreamTwo. \
Wraps ALSA output and the AMLogic hardware A/V sync engine (/sys/class/tsync). \
Drop-in replacement pattern for dvbaudiosink in enigma2 servicemp3."
SECTION = "multimedia"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=053f8b6b820e73326cbf1feaece6909e"

DEPENDS = "glib-2.0-native gstreamer1.0 gstreamer1.0-plugins-base alsa-lib ffmpeg"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "^(dreamone|dreamtwo)$"

SRC_URI = " \
    file://README.md;subdir=${BP} \
    file://COPYING;subdir=${BP} \
    file://configure.ac;subdir=${BP} \
    file://Makefile.am;subdir=${BP} \
    file://autogen.sh;subdir=${BP} \
    file://src/Makefile.am;subdir=${BP} \
    file://src/plugin.c;subdir=${BP} \
    file://src/gstdreamaudiosink.c;subdir=${BP} \
    file://src/gstdreamaudiosink.h;subdir=${BP} \
    file://src/dream_decoder.c;subdir=${BP} \
    file://src/dream_decoder.h;subdir=${BP} \
    file://src/dream_alsa.c;subdir=${BP} \
    file://src/dream_alsa.h;subdir=${BP} \
    file://src/dream_avsync.c;subdir=${BP} \
    file://src/dream_avsync.h;subdir=${BP} \
"

S = "${UNPACKDIR}/${BP}"

inherit autotools pkgconfig

do_configure:prepend() {
    chmod +x ${S}/autogen.sh
    mkdir -p ${S}/m4
}

FILES:${PN}      = "${libdir}/gstreamer-1.0/*.so"
FILES:${PN}-dev += "${libdir}/gstreamer-1.0/*.la"
FILES:${PN}-dbg += "${libdir}/gstreamer-1.0/.debug"

pkg_preinst:${PN}:prepend () {
    if [ -d "/.cache/gstreamer-1.0" ]; then
        rm -rf "/.cache/gstreamer-1.0"
    fi
    if [ -d "/home/root/.cache/gstreamer-1.0" ]; then
        rm -rf "/home/root/.cache/gstreamer-1.0"
    fi
}
