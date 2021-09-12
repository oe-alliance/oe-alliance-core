GST_VERSION_FULL = "1.18.5"

inherit gitpkgv meson gettext

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "71c213f99ad5c8fc8f36a26238483f2bdf7d846a"
SRCREV_gst_libav = "9db917cab4c20e72e53ed962406a0d94636a2a39"

SRCREV_gst_plugins_bad = "d3af58d5b31941caa26c3ded85d7a7b84a91f0cc"
SRCREV_gst_plugins_base = "57fb883b3f8c6d7a397afc0dfc4a7c2e5af05579"
SRCREV_gst_plugins_good = "56dec037a80266add6853e4b06e2dc379de757d1"
SRCREV_gst_plugins_ugly = "bd1dfb44475ab04d42b217acdf90942e0b7893b5"

S = "${WORKDIR}/git"
