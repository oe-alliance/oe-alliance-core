GST_VERSION_FULL = "1.17.0.1"

inherit gitpkgv meson gettext

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "f018b4eddcbf27b62f7d3ec1cd4dbf3855b8a202"
SRCREV_gst_libav = "b7d450b11834d0a510fdcb661ce105860338e2d6"

SRCREV_gst_plugins_bad = "287f9b18b0817e2464da22b683b72102bb81b648"
SRCREV_gst_plugins_base = "ae2b4f8899a65377e10aa1156dff510b8ea665ae"
SRCREV_gst_plugins_good = "c4f6ce789dd614d2a4c782620b2e7ddd734e1dd7"
SRCREV_gst_plugins_ugly = "9273903286b0fe7a9d8a5279c44a5e149e55ff24"

SRCREV_gst_ffmpeg = "bd71f039a349b4a448ae7f0eddb65fd8f923345b"

S = "${WORKDIR}/git"
