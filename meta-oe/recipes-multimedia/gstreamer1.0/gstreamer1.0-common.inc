GST_VERSION_FULL = "1.19.0.1"

inherit gitpkgv meson gettext

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "12bc72880530748b34bd414473db25c6bdf8e74a"
SRCREV_gst_libav = "f29e9dfe9cc46e4e3b6340c782a1a0a479f9a400"

SRCREV_gst_plugins_bad = "d1e72901091b12e7cc5a4efe7406af04fe0477f8"
SRCREV_gst_plugins_base = "0dc419197f95d8e1b7f8f5685d07fdeb21da3ea7"
SRCREV_gst_plugins_good = "2c76f03fe28d7b03df96cd65b29a20aa5025d51c"
SRCREV_gst_plugins_ugly = "a9105ad1e1fb8cbcf787c2a967697707eea405ed"

S = "${WORKDIR}/git"
