GST_VERSION_FULL = "1.17.0.1"

inherit gitpkgv

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "3f93290151230947440f2979aa412f2e542caee8"
SRCREV_gst_common = "f32031c0c1eefb227ab5ce77722a23bf1fe59891"
SRCREV_gst_libav = "6382be0996ee92f8214c89d2028c28dc4806c84e"

SRCREV_gst_plugins_bad = "8257159909c6b7b74eb2e468d74806ff000dff21"
SRCREV_gst_plugins_base = "72eb7c6a74508525598c3ff2545aedeba0f54b87"
SRCREV_gst_plugins_good = "e18d5d6ec6dd883e65a4a58fcf96d5144512c6ca"
SRCREV_gst_plugins_ugly = "f31f085e7bec95422f95056e2391fe1b7c26ee32"

SRCREV_ffmpeg = "75384bc464411fb2291105dca84b8bc411188350"

SRC_URI_append = " git://gitlab.freedesktop.org/gstreamer/common;protocol=https;branch=master;destsuffix=git/common;name=gst_common"

S = "${WORKDIR}/git"

do_configure_prepend() {
    cd ${S}
    ./autogen.sh --noconfigure
    cd ${B}
}
