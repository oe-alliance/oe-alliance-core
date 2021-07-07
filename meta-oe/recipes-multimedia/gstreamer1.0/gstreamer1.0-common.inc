GST_VERSION_FULL = "1.19.1.1"

inherit gitpkgv meson gettext

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "8c08cdf9fe911489d4cfc7b61dd8ff75f7cb08e5"
SRCREV_gst_libav = "678773bb6468e815c180ff82a09d863451e89ba8"

SRCREV_gst_plugins_bad = "078b2699e7b741bb5c3dcac843573fd1ccea0195"
SRCREV_gst_plugins_base = "aa34710bb6975f0ab7f25cb17c4f93366f8f01e1"
SRCREV_gst_plugins_good = "ce0be27caf69aa9d96b73bc2b50737451b6f6936"
SRCREV_gst_plugins_ugly = "cc1a7e2c4d10ab118d635a9db163440bc9d96dd0"

S = "${WORKDIR}/git"
