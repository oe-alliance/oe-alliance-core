GST_VERSION_FULL = "1.17.0.1"

inherit gitpkgv meson gettext

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "8a20d66b1065781c40679356f64e114fe49f7435"
SRCREV_gst_libav = "9e56619b10e741dc7ffaba63c3001536fe3a8b66"

SRCREV_gst_plugins_bad = "6af38c6ffe8d77dbc403516e42589174bbcb0f3c"
SRCREV_gst_plugins_base = "b4e37d8a4b5a8672fa5bede013eb7df078e2e47f"
SRCREV_gst_plugins_good = "a6e28ca268af29484ff0638d0f55eb8b36566f5b"
SRCREV_gst_plugins_ugly = "3cd445a490cc36eb8c64000cb384f78f9ab7e7af"

SRCREV_gst_ffmpeg = "b25fc92ead37b0d087ec5af41d391886b814fef7"

S = "${WORKDIR}/git"
