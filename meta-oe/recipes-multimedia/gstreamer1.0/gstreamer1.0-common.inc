GST_VERSION_FULL = "1.19.0.1"

inherit gitpkgv meson gettext

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "e74040b41e724c527610cc53e295abe91b99d615"
SRCREV_gst_libav = "b6acd99239247aeb3dbb433508316ba709f550bb"

SRCREV_gst_plugins_bad = "d1f397e7546f2c63069993f55966a43464976e3f"
SRCREV_gst_plugins_base = "660b5e4a9864b843c3bc26cd6cdf7b42192f1f36"
SRCREV_gst_plugins_good = "aa264e6b073b612feae3289699c61c5327b8a458"
SRCREV_gst_plugins_ugly = "a798b7d6a023e8e8a6dd452653bbcba1e4d39b77"

S = "${WORKDIR}/git"
