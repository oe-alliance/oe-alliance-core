GST_VERSION_FULL = "1.17.0.1"

inherit gitpkgv meson gettext

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "8140da74ba474d2f218c0c85944c89d6be38e545"
SRCREV_gst_libav = "b7d450b11834d0a510fdcb661ce105860338e2d6"

SRCREV_gst_plugins_bad = "2a3205b294cb881bb1d60ad9bf7c9841031fb2f4"
SRCREV_gst_plugins_base = "940c9998e5cfffd90fade8a7f39f29a426e20460"
SRCREV_gst_plugins_good = "b928517f1ebbe89cce903daa0011bb1307e6aabd"
SRCREV_gst_plugins_ugly = "a47b6b5f3c215e05f93617085db03b5a4a9ac72f"

SRCREV_gst_ffmpeg = "b25fc92ead37b0d087ec5af41d391886b814fef7"

S = "${WORKDIR}/git"
