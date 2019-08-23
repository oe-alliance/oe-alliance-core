GST_VERSION_FULL = "1.17.0.1"

inherit gitpkgv

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "a4d5c95638b0783124e983d5ba063bb38c6f53a3"
SRCREV_gst_common = "f32031c0c1eefb227ab5ce77722a23bf1fe59891"
SRCREV_gst_libav = "06c249cabb41d5de6394569bfb545758fec07749"

SRCREV_gst_plugins_bad = "2b478d3423f75bb81b1bab2ce44f007701ba5825"
SRCREV_gst_plugins_base = "e29e57b77cbe785d6fba656f1acf53a385075d96"
SRCREV_gst_plugins_good = "53dc1da870f5896c94567b432ab484722d3850e1"
SRCREV_gst_plugins_ugly = "8f5b7a93b43441f18af079dc24a5d5b23b5ad93b"

SRCREV_ffmpeg = "370c346d5dfd28d548b7ce6560e1448ce796b7fe"

SRC_URI_append = " git://gitlab.freedesktop.org/gstreamer/common;protocol=https;branch=master;destsuffix=git/common;name=gst_common"

S = "${WORKDIR}/git"

do_configure_prepend() {
    cd ${S}
    ./autogen.sh --noconfigure
    cd ${B}
}
