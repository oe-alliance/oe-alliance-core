GST_VERSION_FULL = "1.19.0.1"

inherit gitpkgv meson gettext

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "defe732ae0ccc61cfb2f98f0768bba459dbf08af"
SRCREV_gst_libav = "1b81e05ab9203fead9fbca4997703b2ed6484ec2"

SRCREV_gst_plugins_bad = "81d4ccdc44445e832f51d02bcc4950b9bef71214"
SRCREV_gst_plugins_base = "a5e2883ff0286142ee697077cb740578f361e6e3"
SRCREV_gst_plugins_good = "a75cfa522deabf6cbed2af3ef4bf801b23ae9cde"
SRCREV_gst_plugins_ugly = "3ff44eabc3589584ed73ba77ce34e62042d6e578"

S = "${WORKDIR}/git"
