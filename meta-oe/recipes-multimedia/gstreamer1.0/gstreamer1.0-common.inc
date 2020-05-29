GST_VERSION_FULL = "1.17.0.1"

inherit gitpkgv meson gettext

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "0cccf63fa048c2b03397a76ff3eee4e02b7b5a7a"
SRCREV_gst_libav = "a5850215babe4359a032fcb7d2e614a6209fa1f6"

SRCREV_gst_plugins_bad = "00caf46e3fd5e517c0f2272b809dea949de550bf"
SRCREV_gst_plugins_base = "9a94b4cbc12a2995bdcc7d967d2d3be4012ba468"
SRCREV_gst_plugins_good = "b94b9988fa0219566274214779887f33644167b3"
SRCREV_gst_plugins_ugly = "4063a319c5a7a3ffe23047c378ec19b2b9ed2ef5"

# gst ffmpeg meson port v4.1.5
SRCREV_gst_ffmpeg = "5da8bab7d28e41e2ae5bbb3ef7bc8f289a6dd692"

S = "${WORKDIR}/git"
