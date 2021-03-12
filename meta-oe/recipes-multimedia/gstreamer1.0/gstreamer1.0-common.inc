GST_VERSION_FULL = "1.19.0.1"

inherit gitpkgv meson gettext

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "44bdad58f623e50a07476c0f40f8ff7543396f7c"
SRCREV_gst_libav = "1d33d1c8eaece0d02c4352fc0e663b3628cc42d1"

SRCREV_gst_plugins_bad = "451c875d40a92ade05389cb62ef885eefd29be4a"
SRCREV_gst_plugins_base = "9759810d8206b5f1aa199f98599caec3630a1813"
SRCREV_gst_plugins_good = "78dec1e4039c27b37ce62e70faa84d3f84543a0c"
SRCREV_gst_plugins_ugly = "a9105ad1e1fb8cbcf787c2a967697707eea405ed"

S = "${WORKDIR}/git"
