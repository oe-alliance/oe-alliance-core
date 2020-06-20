GST_VERSION_FULL = "1.17.1.1"

inherit gitpkgv meson gettext

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "74d29ca77117580bbc63cf8fd3af9e68d9bb9dbf"
SRCREV_gst_libav = "e2c4828699911d906640fde09c185693f3848e7b"

SRCREV_gst_plugins_bad = "b5f7f8174f4d7efc12ff0ec33b660063e4a8fb4a"
SRCREV_gst_plugins_base = "8768efde64b996b8e1db5efa70a13114e6895a65"
SRCREV_gst_plugins_good = "9e0cdc6ff7aac0a0c8dec1cc111834107a3b476b"
SRCREV_gst_plugins_ugly = "83e97119730ea08963c17ac55fc95ff184dd0fe9"

S = "${WORKDIR}/git"
