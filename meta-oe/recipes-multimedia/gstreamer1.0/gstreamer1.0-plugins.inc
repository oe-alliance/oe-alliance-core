SUMMARY = "Plugins for the GStreamer multimedia framework 1.x"
HOMEPAGE = "http://gstreamer.freedesktop.org/"
BUGTRACKER = "https://bugzilla.gnome.org/enter_bug.cgi?product=Gstreamer"
SECTION = "multimedia"

require gstreamer1.0-common.inc
require gstreamer1.0-plugins-packaging.inc

DEPENDS = "gstreamer1.0 glib-2.0-native"

SRC_URI_append = " file://0001-meson-build-optimization.patch"

inherit pkgconfig

# Orc enables runtime JIT compilation of data processing routines from Orc
# bytecode to SIMD instructions for various architectures (currently SSE, MMX,
# MIPS, Altivec and NEON are supported).

GSTREAMER_ORC ?= "orc"

PACKAGECONFIG[debug] = "-Ddebug=true,-Ddebug=false"
PACKAGECONFIG[orc] = "-Dorc=enabled,-Dorc=disabled,orc orc-native"

export ORCC = "${STAGING_DIR_NATIVE}${bindir}/orcc"

EXTRA_OEMESON = " \
    -Ddoc=disabled \
    -Dexamples=disabled \
"

CFLAGS_append = " -Wno-deprecated-declarations"
TARGET_CFLAGS_append_sh4 = " -std=gnu99"

PACKAGES_DYNAMIC = "^${PN}-.*"
