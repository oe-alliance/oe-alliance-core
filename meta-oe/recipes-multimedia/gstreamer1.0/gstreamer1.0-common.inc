GST_VERSION_FULL = "1.17.0.1"

inherit gitpkgv meson gettext

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "606a9acd74f91160a4668e70ebe6acdcfb7128ab"
SRCREV_gst_libav = "39e6f846e0e867185437925bf766a6ba10e6404f"

SRCREV_gst_plugins_bad = "387b6df948a566ca8d6318b58029f66499ffb1c2"
SRCREV_gst_plugins_base = "fef4717770f4f829ef8d23ffd0f4797f7522eb37"
SRCREV_gst_plugins_good = "5817c659e67c98f1d32db9d630ea1103ddd2b261"
SRCREV_gst_plugins_ugly = "15440f2ddc76faf61a7cdbc72e7b34f69b497415"

# ffmpeg versions < 4.2.2
SRCREV_gst_ffmpeg = "bd71f039a349b4a448ae7f0eddb65fd8f923345b"

S = "${WORKDIR}/git"
