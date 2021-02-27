GST_VERSION_FULL = "1.19.0.1"

inherit gitpkgv meson gettext

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "3e275580957815195d356408fbbcb891f9fe5777"
SRCREV_gst_libav = "1d33d1c8eaece0d02c4352fc0e663b3628cc42d1"

SRCREV_gst_plugins_bad = "771645e4450080b13a54f79e41a2192ae67ee235"
SRCREV_gst_plugins_base = "e99a6f3142e68e4933f0f8fb25707b0babc162cd"
SRCREV_gst_plugins_good = "e885e0e1d78b199280c8252cbf726b8a81393aba"
SRCREV_gst_plugins_ugly = "a9105ad1e1fb8cbcf787c2a967697707eea405ed"

S = "${WORKDIR}/git"
