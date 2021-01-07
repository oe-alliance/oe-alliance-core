GST_VERSION_FULL = "1.19.0.1"

inherit gitpkgv meson gettext

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "384fba13f499fd293f0efd094f681bc766546dad"
SRCREV_gst_libav = "8f2cab6c03ea095b9fe728e83264ae1e119db009"

SRCREV_gst_plugins_bad = "4e7f7871db7be4e6d7a43a68a7454b4c754299a5"
SRCREV_gst_plugins_base = "f573d91237edd2389a42d305bc1150dcc49397f6"
SRCREV_gst_plugins_good = "79efd372c12ae9117f3863d756296a22a4010c0f"
SRCREV_gst_plugins_ugly = "a9105ad1e1fb8cbcf787c2a967697707eea405ed"

S = "${WORKDIR}/git"
