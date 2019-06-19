GST_VERSION_FULL = "1.17.0.1"

inherit gitpkgv

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "0257c7813b3d13081ff107070124ebc79dc7baf9"
SRCREV_gst_common = "f32031c0c1eefb227ab5ce77722a23bf1fe59891"
SRCREV_gst_libav = "2596e5bd6f1f1d51331f5e7e7f1fa98e4ed02d30"

SRCREV_gst_plugins_bad = "324e55a3cdd7c6cef24356fd626deee5fba343df"
SRCREV_gst_plugins_base = "197dcb9bf4d8fa9218b2b85e5e9f08866bf44708"
SRCREV_gst_plugins_good = "91e858dcbe3707111838d3d79d62028eea52a08f"
SRCREV_gst_plugins_ugly = "985ace70e05f664d13e268cf0fb135d19b29fff8"

SRCREV_ffmpeg = "72f03b2af489af4c6d2efc6006629d1f2dabdead"

SRC_URI_append = " git://gitlab.freedesktop.org/gstreamer/common;protocol=https;branch=master;destsuffix=git/common;name=gst_common"

S = "${WORKDIR}/git"

do_configure_prepend() {
    cd ${S}
    ./autogen.sh --noconfigure
    cd ${B}
}
