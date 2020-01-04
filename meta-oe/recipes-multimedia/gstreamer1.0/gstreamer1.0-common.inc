GST_VERSION_FULL = "1.17.0.1"

inherit gitpkgv meson gettext

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "19b28788cfd15c1382cc7a6bf6621b246def012f"
SRCREV_gst_libav = "b7d450b11834d0a510fdcb661ce105860338e2d6"

SRCREV_gst_plugins_bad = "4e6a1b9634954f2382c29d93ec0ce1cddc7da620"
SRCREV_gst_plugins_base = "c7a9a966b030aec082c1a4ac17ab5dea60e9f501"
SRCREV_gst_plugins_good = "bb1ce82e39bd3821d7d52047bec6550b0fb3a300"
SRCREV_gst_plugins_ugly = "a47b6b5f3c215e05f93617085db03b5a4a9ac72f"

SRCREV_gst_ffmpeg = "b25fc92ead37b0d087ec5af41d391886b814fef7"

S = "${WORKDIR}/git"
