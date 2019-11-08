SUMMARY = "GStreamer 1.0 multimedia framework"
DESCRIPTION = "GStreamer is a multimedia framework for encoding and decoding video and sound. \
It supports a wide range of formats including mp3, ogg, avi, mpeg and quicktime."
HOMEPAGE = "http://gstreamer.freedesktop.org/"
BUGTRACKER = "https://bugzilla.gnome.org/enter_bug.cgi?product=Gstreamer"
SECTION = "multimedia"
LICENSE = "LGPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=6762ed442b3822387a51c92d928ead0d \
                    file://gst/gst.h;beginline=1;endline=21;md5=e059138481205ee2c6fc1c079c016d0d \
                    "

require gstreamer1.0-common.inc

DEPENDS = "bison-native flex-native glib-2.0 glib-2.0-native gobject-introspection libxml2 libcap"

inherit pkgconfig gettext

SRCREV_FORMAT = "gst"

SRC_URI = "git://gitlab.freedesktop.org/gstreamer/gstreamer;protocol=https;branch=master;name=gst \
           file://0001-meson-build-optimization.patch \
           file://0002-gst-gstpluginloader.c-when-env-var-is-set-do-not-fal.patch \
           file://0001-revert-use-new-gst-adapter-get-buffer.patch \
           file://0002-continue-on-nondefined-64bit-atomics.patch \
"

PACKAGECONFIG ??= ""

PACKAGECONFIG[debug] = "-Ddebug=true,-Ddebug=false"
PACKAGECONFIG[tests] = "-Dtests=enabled,-Dtests=disabled"
PACKAGECONFIG[tracer_hooks] = "-Dtracer_hooks=true,-Dtracer_hooks=false,"
PACKAGECONFIG[unwind] = "-Dlibunwind=enabled,-Dlibunwind=disabled,libunwind"
PACKAGECONFIG[dw] = "-Dlibdw=enabled,-Dlibdw=disabled,elfutils"

EXTRA_OEMESON = " \
    -Ddoc=disabled \
    -Dgtk_doc=disabled \
    -Dexamples=disabled \
    --libexecdir=${libdir} \
"

PACKAGES += "${PN}-bash-completion"

FILES_${PN} += "${libdir}/gstreamer-1.0/*.so ${libdir}/gstreamer-1.0/gst-*"
FILES_${PN}-dev += "${libdir}/gstreamer-1.0/*.la ${libdir}/gstreamer-1.0/*.a ${libdir}/gstreamer-1.0/include"
FILES_${PN}-bash-completion += "${datadir}/bash-completion/completions/ ${datadir}/bash-completion/helpers/gst*"
FILES_${PN}-dbg += "${datadir}/gdb ${datadir}/gstreamer-1.0"

RRECOMMENDS_${PN}_qemux86 += "kernel-module-snd-ens1370 kernel-module-snd-rawmidi"
RRECOMMENDS_${PN}_qemux86-64 += "kernel-module-snd-ens1370 kernel-module-snd-rawmidi"
