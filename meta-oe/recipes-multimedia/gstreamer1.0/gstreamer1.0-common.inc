GST_VERSION_FULL = "1.17.0.1"

inherit gitpkgv

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "5f89225bc2766b6cfb4ce1d5a09a0e8276e02d1e"
SRCREV_gst_common = "f32031c0c1eefb227ab5ce77722a23bf1fe59891"
SRCREV_gst_libav = "2596e5bd6f1f1d51331f5e7e7f1fa98e4ed02d30"

SRCREV_gst_plugins_bad = "b756b13ee2541e9899536c0594475dee6026488a"
SRCREV_gst_plugins_base = "0b44ac28b749913afac227175491a0fe7e9ac344"
SRCREV_gst_plugins_good = "2c3c1072f788d860d98dc33a3e9703d8fbdfee35"
SRCREV_gst_plugins_ugly = "985ace70e05f664d13e268cf0fb135d19b29fff8"

SRCREV_ffmpeg = "6872daee87b9b7a8adcb3bd3b1defea6f2153d2b"

SRC_URI_append = " git://gitlab.freedesktop.org/gstreamer/common;protocol=https;branch=master;destsuffix=git/common;name=gst_common"

S = "${WORKDIR}/git"

do_configure_prepend() {
    cd ${S}
    ./autogen.sh --noconfigure
    cd ${B}
}
