GST_VERSION_FULL = "1.18.6"

inherit gitpkgv meson gettext

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "793792d5cd6be0297adec74ad35a87b764812c0b"
SRCREV_gst_libav = "9b31c42bebe36e4f88b2b6f1bd4f98f62708d793"

SRCREV_gst_plugins_bad = "5d07db447c33fe217a6c7f8a910307c236ce1674"
SRCREV_gst_plugins_base = "2d5385f55a7d29975039692629bde3981e5d29cd"
SRCREV_gst_plugins_good = "7747342a54ae56013a297b6554bfb3f12bde6784"
SRCREV_gst_plugins_ugly = "8f97a05e1d2cb3cd2548cde2e70ea6d335040f59"

S = "${WORKDIR}/git"
