GST_VERSION_FULL = "1.17.0.1"

inherit gitpkgv meson gettext

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "19f414c0d1885d4178a17d05f4a6db1e20ed6496"
SRCREV_gst_libav = "c8b88847919c9cb26205d91111fa11d4d4f44883"

SRCREV_gst_plugins_bad = "26ac42f7c07c5ed15d9309b49417a3e968ba774f"
SRCREV_gst_plugins_base = "a12f161724835b263d20475dfb074065141eed69"
SRCREV_gst_plugins_good = "42e7864e90ce40059efc192e375ada9dada15701"
SRCREV_gst_plugins_ugly = "9273903286b0fe7a9d8a5279c44a5e149e55ff24"

S = "${WORKDIR}/git"
