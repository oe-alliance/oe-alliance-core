GST_VERSION_FULL = "1.17.0.1"

inherit gitpkgv meson gettext

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "66819febd9b5438b12fce5dc0f6a1ff732f46a82"
SRCREV_gst_libav = "c8b88847919c9cb26205d91111fa11d4d4f44883"

SRCREV_gst_plugins_bad = "9bf4746e2f4c5ce51a5a9ef6469521bbb10d47b0"
SRCREV_gst_plugins_base = "0e1f7f6e8d09b2d76f6afa941b2574ea9ab1012e"
SRCREV_gst_plugins_good = "1471100f37b6cf2382baaecb9d79f86260006829"
SRCREV_gst_plugins_ugly = "9273903286b0fe7a9d8a5279c44a5e149e55ff24"

SRCREV_gst_ffmpeg = "bd71f039a349b4a448ae7f0eddb65fd8f923345b"

S = "${WORKDIR}/git"
