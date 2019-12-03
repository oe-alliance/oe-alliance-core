GST_VERSION_FULL = "1.17.0.1"

inherit gitpkgv meson gettext

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "a9162fc2eda614aac85670939f352bd2107bda37"
SRCREV_gst_libav = "77f63c2457952ce6883002afe0dc9abe82b5e4e7"

SRCREV_gst_plugins_bad = "fd6c51b2e7eb55cdf43a7cb98f51530314356530"
SRCREV_gst_plugins_base = "2c7445b9cca91022ae1b5104dd7f6f716ca797f1"
SRCREV_gst_plugins_good = "a7c887b1975401d0fa4118ff8257eca25a6d8508"
SRCREV_gst_plugins_ugly = "3cd445a490cc36eb8c64000cb384f78f9ab7e7af"

SRCREV_gst_ffmpeg = "b25fc92ead37b0d087ec5af41d391886b814fef7"

S = "${WORKDIR}/git"
