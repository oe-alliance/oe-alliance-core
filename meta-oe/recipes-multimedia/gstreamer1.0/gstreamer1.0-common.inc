GST_VERSION_FULL = "1.18.0"

inherit gitpkgv meson gettext

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "96148da56f9cbf23120e51ce59ab90f94d8b19b8"
SRCREV_gst_libav = "215b3ed959f2b307065319f94855cc9e1ce7be95"

SRCREV_gst_plugins_bad = "7cb583bb0427819a6b59b783e7df67961df2155f"
SRCREV_gst_plugins_base = "f21623c1f60949ed6a77e2c2c3857be942cd2db5"
SRCREV_gst_plugins_good = "6ef694ce7b40dda9f2163ed5e1b1de7378505ad0"
SRCREV_gst_plugins_ugly = "ae91a81d9aa913cee1e8310af93a8fff5445628d"

S = "${WORKDIR}/git"
