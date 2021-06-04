GST_VERSION_FULL = "1.19.1.1"

inherit gitpkgv meson gettext

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "aaaf85054f1235d2c22ab19ad909f98edf96cacd"
SRCREV_gst_libav = "2d9b73b76b6c8db6b394714e20332b95d4cb6c74"

SRCREV_gst_plugins_bad = "a997524e3fbfc1e0ee5bc4aa33ee6666f8b163af"
SRCREV_gst_plugins_base = "c18dd84f796983dd1cebc1e05eaf6edcc8ee78e3"
SRCREV_gst_plugins_good = "be83a52db953850ffccbb3868dd660e2aa129e5f"
SRCREV_gst_plugins_ugly = "cc1a7e2c4d10ab118d635a9db163440bc9d96dd0"

S = "${WORKDIR}/git"
