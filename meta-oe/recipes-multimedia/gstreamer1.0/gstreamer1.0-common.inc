GST_VERSION_FULL = "1.17.0.1"

inherit gitpkgv

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "09909d37bdcac38432c1de2ff7b4b0a6a155e14e"
SRCREV_gst_common = "f32031c0c1eefb227ab5ce77722a23bf1fe59891"
SRCREV_gst_libav = "2596e5bd6f1f1d51331f5e7e7f1fa98e4ed02d30"

SRCREV_gst_plugins_bad = "af360dc641b08351b12e078d3d2f1b339721ae9b"
SRCREV_gst_plugins_base = "8f125fb4d9616a1f09590eb97950dff96ec33dc3"
SRCREV_gst_plugins_good = "67b8ce31671f48c04fb971f75497ada0df9d187c"
SRCREV_gst_plugins_ugly = "985ace70e05f664d13e268cf0fb135d19b29fff8"

SRCREV_ffmpeg = "9d06c1f95ebe4f9c2cc05d041dbfd3de52d2518a"

SRC_URI_append = " git://gitlab.freedesktop.org/gstreamer/common;protocol=https;branch=master;destsuffix=git/common;name=gst_common"

S = "${WORKDIR}/git"

do_configure_prepend() {
    cd ${S}
    ./autogen.sh --noconfigure
    cd ${B}
}
