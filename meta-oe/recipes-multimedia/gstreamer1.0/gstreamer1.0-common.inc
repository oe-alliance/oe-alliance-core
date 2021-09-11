GST_VERSION_FULL = "1.18.5"

inherit gitpkgv meson gettext

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "4c7bc588e3233c3b0fbd95af03612d6dc1803b0e"
SRCREV_gst_libav = "25ba730ad420da85ffca0b46fb078184e7b8f25a"

SRCREV_gst_plugins_bad = "f2ef2dc4d5c7889c7a0d1c2152a95cab0da524b9"
SRCREV_gst_plugins_base = "4b101d8318fc578a0f0ee4f16d72099419170c6f"
SRCREV_gst_plugins_good = "0c07f76580f5d555c7a23fd95558668f9d84fbd6"
SRCREV_gst_plugins_ugly = "6eabe7821280afc11b7b0d66d85132ff8251adda"

S = "${WORKDIR}/git"
