GST_VERSION_FULL = "1.17.2.1"

inherit gitpkgv meson gettext

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "eea1ac156c305efbb47a96bb104b4eb585f9a0df"
SRCREV_gst_libav = "83190c4ec0041db0c10a629b51f8f9881fac9300"

SRCREV_gst_plugins_bad = "31d5d04bb1f5e3f6acdef8460193019237ecf5df"
SRCREV_gst_plugins_base = "ad55d3ce9d80e5c254d97ae21c340bb249b7f7c3"
SRCREV_gst_plugins_good = "c194390ce9f77adc33231243909e9e59885c1ba0"
SRCREV_gst_plugins_ugly = "59a7742cff8c8309176523edf6445e5a07e9fef0"

S = "${WORKDIR}/git"
