GST_VERSION_FULL = "1.19.1.1"

inherit gitpkgv meson gettext

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "e9d06cec5a63750b1be5e9fa9426f456bd9cfcf9"
SRCREV_gst_libav = "72d0d288115e25fc21bfe0113d8cea7598cc08ab"

SRCREV_gst_plugins_bad = "fbf6bfd4d8270d110ad6a8657d3b0879994d524b"
SRCREV_gst_plugins_base = "04856a3828411fe23fc043787b7687c0416e51fc"
SRCREV_gst_plugins_good = "9af6ce974a7f2dc0bee37fcd4763842669aee723"
SRCREV_gst_plugins_ugly = "fbe2ea6e3868850388d06e66ef2681419e27f2fa"

S = "${WORKDIR}/git"
