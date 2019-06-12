GST_VERSION_FULL = "1.17.0.1"

inherit gitpkgv

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "6d16615093fcd2bb27e0725066f084adbb106be2"
SRCREV_gst_common = "f32031c0c1eefb227ab5ce77722a23bf1fe59891"
SRCREV_gst_libav = "8e948f9e148a7ee54c00cbf34e3f3329adf83557"

SRCREV_gst_plugins_bad = "428b8f34570d89878b243a78cf4a222aec6e9807"
SRCREV_gst_plugins_base = "303f927d707a8625c6ce29eceebd09bab055d992"
SRCREV_gst_plugins_good = "f6b91fe30317cd0163b3d35c7528eae5892f5af2"
SRCREV_gst_plugins_ugly = "985ace70e05f664d13e268cf0fb135d19b29fff8"

SRCREV_ffmpeg = "7211e1ca9367f7f1a4a04983a66c7b1e2c9a3c92"

SRC_URI_append = " git://gitlab.freedesktop.org/gstreamer/common;protocol=https;branch=master;destsuffix=git/common;name=gst_common"

S = "${WORKDIR}/git"

do_configure_prepend() {
    cd ${S}
    ./autogen.sh --noconfigure
    cd ${B}
}
