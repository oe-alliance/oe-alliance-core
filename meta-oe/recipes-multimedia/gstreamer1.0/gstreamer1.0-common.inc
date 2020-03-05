GST_VERSION_FULL = "1.17.0.1"

inherit gitpkgv meson gettext

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "0a185c24b5a416ef6163ea4b82675fba58956058"
SRCREV_gst_libav = "c8b88847919c9cb26205d91111fa11d4d4f44883"

SRCREV_gst_plugins_bad = "ad53de1da193129f1874a9908ae44457632c818c"
SRCREV_gst_plugins_base = "51253830f3e9dad108baa07c87ed62509ee3123f"
SRCREV_gst_plugins_good = "4046970b01bb524007945c8cbb5d7977f256f16e"
SRCREV_gst_plugins_ugly = "9273903286b0fe7a9d8a5279c44a5e149e55ff24"

SRCREV_gst_ffmpeg = "bd71f039a349b4a448ae7f0eddb65fd8f923345b"

S = "${WORKDIR}/git"
