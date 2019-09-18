GST_VERSION_FULL = "1.17.0.1"

inherit gitpkgv

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "0dafe6e63971ea89a9a9ff9cf30f5a076f915259"
SRCREV_gst_common = "f32031c0c1eefb227ab5ce77722a23bf1fe59891"
SRCREV_gst_libav = "f4859ff73df9655517a36d0ef1e4fcd732307ace"

SRCREV_gst_plugins_bad = "2af2402880bf75977a2e7313483d68d1d1a76dcc"
SRCREV_gst_plugins_base = "dff5399e8750bf0f48efd23370f09aeddeb1d7d8"
SRCREV_gst_plugins_good = "505c88eeb6aa8e7df9dd7596510af6b098b00e74"
SRCREV_gst_plugins_ugly = "b3f0008b3431f5bd1ef99bfcc35de00be93d865d"

SRCREV_ffmpeg = "1529dfb73a5157dcb8762051ec4c8d8341762478"

SRC_URI_append = " git://gitlab.freedesktop.org/gstreamer/common;protocol=https;branch=master;destsuffix=git/common;name=gst_common"

S = "${WORKDIR}/git"

do_configure_prepend() {
    cd ${S}
    ./autogen.sh --noconfigure
    cd ${B}
}
