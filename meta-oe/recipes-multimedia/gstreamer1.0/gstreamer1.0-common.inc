GST_VERSION_FULL = "1.17.0.1"

inherit gitpkgv meson gettext

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "48f14c5e5ea1420795984d28bed1cf9fd7fb2711"
SRCREV_gst_libav = "34b1eb117faa5cc2f13fe1782abd541f4594645c"

SRCREV_gst_plugins_bad = "0478e2dc1a3d2d097aa1ca6f221b460236f879aa"
SRCREV_gst_plugins_base = "37c996dcf49702774302c69855ed6f573fa74de7"
SRCREV_gst_plugins_good = "c4f6ce789dd614d2a4c782620b2e7ddd734e1dd7"
SRCREV_gst_plugins_ugly = "9273903286b0fe7a9d8a5279c44a5e149e55ff24"

SRCREV_gst_ffmpeg = "bd71f039a349b4a448ae7f0eddb65fd8f923345b"

S = "${WORKDIR}/git"
