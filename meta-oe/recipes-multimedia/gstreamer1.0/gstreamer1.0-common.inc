GST_VERSION_FULL = "1.17.0.1"

inherit gitpkgv meson gettext

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "4af103d124310ca8c4c101c890e309592031e513"
SRCREV_gst_libav = "77f63c2457952ce6883002afe0dc9abe82b5e4e7"

SRCREV_gst_plugins_bad = "526afac73681450a7dae4d9b8db9fcb039da5311"
SRCREV_gst_plugins_base = "3b8769e67351f079184b24b7b04b327fd3f16aad"
SRCREV_gst_plugins_good = "b44d37a3387398a134140a4caa64919e95f7f7e3"
SRCREV_gst_plugins_ugly = "3cd445a490cc36eb8c64000cb384f78f9ab7e7af"

SRCREV_gst_ffmpeg = "b25fc92ead37b0d087ec5af41d391886b814fef7"

S = "${WORKDIR}/git"
