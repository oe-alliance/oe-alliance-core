GST_VERSION_FULL = "1.17.0.1"

inherit gitpkgv

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "ce7e6a94067604d2cd819bcf7df5ba8c43131672"
SRCREV_gst_common = "f32031c0c1eefb227ab5ce77722a23bf1fe59891"
SRCREV_gst_libav = "8e948f9e148a7ee54c00cbf34e3f3329adf83557"

SRCREV_gst_plugins_bad = "392f86ae35ad207a170922cc2ff54d928bf97ff0"
SRCREV_gst_plugins_base = "abd80b6561a83bbbc9b54482c146df54b13a8b11"
SRCREV_gst_plugins_good = "ddbbe5d277f2ec0475de9bd6962d5a983eb64b23"
SRCREV_gst_plugins_ugly = "985ace70e05f664d13e268cf0fb135d19b29fff8"

SRCREV_ffmpeg = "7211e1ca9367f7f1a4a04983a66c7b1e2c9a3c92"

SRC_URI_append = " git://gitlab.freedesktop.org/gstreamer/common;protocol=https;branch=master;destsuffix=git/common;name=gst_common"

S = "${WORKDIR}/git"

do_configure_prepend() {
    cd ${S}
    ./autogen.sh --noconfigure
    cd ${B}
}
