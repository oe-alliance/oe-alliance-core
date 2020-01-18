GST_VERSION_FULL = "1.17.0.1"

inherit gitpkgv meson gettext

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "f018b4eddcbf27b62f7d3ec1cd4dbf3855b8a202"
SRCREV_gst_libav = "b7d450b11834d0a510fdcb661ce105860338e2d6"

SRCREV_gst_plugins_bad = "415c798b73964a7051e3decb35a810dc1c995c1d"
SRCREV_gst_plugins_base = "ae2b4f8899a65377e10aa1156dff510b8ea665ae"
SRCREV_gst_plugins_good = "451fc5c1126f98dcd14de357f87bff84f1d8427e"
SRCREV_gst_plugins_ugly = "9273903286b0fe7a9d8a5279c44a5e149e55ff24"

SRCREV_gst_ffmpeg = "97a112afd912609435570890b67c14c6cb682bf4"

S = "${WORKDIR}/git"
