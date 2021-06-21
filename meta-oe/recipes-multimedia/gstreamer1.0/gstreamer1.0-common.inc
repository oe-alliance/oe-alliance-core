GST_VERSION_FULL = "1.19.1.1"

inherit gitpkgv meson gettext

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "d157b23fb0ce175b167611a4cb019599d9017042"
SRCREV_gst_libav = "2d9b73b76b6c8db6b394714e20332b95d4cb6c74"

SRCREV_gst_plugins_bad = "08323f382c61bc7b529ec2eee8f719ffb7fedb95"
SRCREV_gst_plugins_base = "aa8b27d45a5abdcbba4ec4af79aab6009a345ae0"
SRCREV_gst_plugins_good = "aad9c8a2165b0bbee957c54262391006e81907af"
SRCREV_gst_plugins_ugly = "cc1a7e2c4d10ab118d635a9db163440bc9d96dd0"

S = "${WORKDIR}/git"
