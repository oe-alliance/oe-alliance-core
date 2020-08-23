GST_VERSION_FULL = "1.17.90"

inherit gitpkgv meson gettext

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "e97c520f063bb101d4bd2b91ee8940976e2b6ad5"
SRCREV_gst_libav = "93f78bc76bb0494d81c75768e6ef388bbf2adc33"

SRCREV_gst_plugins_bad = "29e969493b45bd957b52affa1c8e055400973196"
SRCREV_gst_plugins_base = "94dcf915e8f856fd468c4c3b6a8e5573c2a6838d"
SRCREV_gst_plugins_good = "6419368b4caa4df769451b7bc7f27f3cada390d6"
SRCREV_gst_plugins_ugly = "bb76cbd5ae78658318f74cb2cf52b24929da1cca"

S = "${WORKDIR}/git"
