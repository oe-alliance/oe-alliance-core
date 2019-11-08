GST_VERSION_FULL = "1.17.0.1"

inherit gitpkgv meson gettext

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "78041a77488a7db8a45db5bc9fff49079e3ab30e"
SRCREV_gst_libav = "07f33470b34c6677b614e543a6d8f8cf0a7ce7e3"

SRCREV_gst_plugins_bad = "b55f380b20f9a1d4d11f92ef5da603f83d250cd6"
SRCREV_gst_plugins_base = "494ee6744655bc2d470e424ab28ae5a0f0866be7"
SRCREV_gst_plugins_good = "b4b79a211fc487d4bae5962721ed812713e9a705"
SRCREV_gst_plugins_ugly = "3cd445a490cc36eb8c64000cb384f78f9ab7e7af"

SRCREV_gst_ffmpeg = "b25fc92ead37b0d087ec5af41d391886b814fef7"

S = "${WORKDIR}/git"
