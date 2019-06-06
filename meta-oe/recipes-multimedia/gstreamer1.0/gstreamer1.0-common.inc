GST_VERSION_FULL = "1.17.0.1"

inherit gitpkgv

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "7cc933dec7b8f775c600a4a8e4013ac37e9bb973"
SRCREV_gst_common = "32edeb4f0e665ccad767ab6a104e013522ce7e6f"
SRCREV_gst_libav = "8e948f9e148a7ee54c00cbf34e3f3329adf83557"

SRCREV_gst_plugins_bad = "69d16ac28d512eb5f9b3a88b6cdb70f201ddc2bc"
SRCREV_gst_plugins_base = "944396af33f20242b43f46bf45e9dd812da7ddb5"
SRCREV_gst_plugins_good = "18a7c10d4e837737fb207b11b9808ea65d281df5"
SRCREV_gst_plugins_ugly = "985ace70e05f664d13e268cf0fb135d19b29fff8"

SRCREV_ffmpeg = "7211e1ca9367f7f1a4a04983a66c7b1e2c9a3c92"

SRC_URI_append = " git://gitlab.freedesktop.org/gstreamer/common;protocol=https;branch=master;destsuffix=git/common;name=gst_common"

S = "${WORKDIR}/git"

do_configure_prepend() {
    cd ${S}
    ./autogen.sh --noconfigure
    cd ${B}
}
