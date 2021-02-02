GST_VERSION_FULL = "1.19.0.1"

inherit gitpkgv meson gettext

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "4a2d1d9c78580f247a35c217f9dc3fe077cd5a38"
SRCREV_gst_libav = "8f2cab6c03ea095b9fe728e83264ae1e119db009"

SRCREV_gst_plugins_bad = "956d6e8ff7eaa663a4f8ebcb6d7d7d6ef3b18e33"
SRCREV_gst_plugins_base = "23370ec429fee5b3bf9a0a932fa135352640bd4e"
SRCREV_gst_plugins_good = "5f00810ee0ba63c54862135e577c8d792f72928c"
SRCREV_gst_plugins_ugly = "a9105ad1e1fb8cbcf787c2a967697707eea405ed"

S = "${WORKDIR}/git"
